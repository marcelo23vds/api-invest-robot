package com.mvieiradev.invest_robot.services;

import com.mvieiradev.invest_robot.infrastructure.entities.Carteira;
import com.mvieiradev.invest_robot.infrastructure.entities.CarteiraItem;
import com.mvieiradev.invest_robot.infrastructure.repository.CarteiraItemRepository;
import com.mvieiradev.invest_robot.infrastructure.repository.CarteiraRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarteiraService {

    private final CarteiraRepository repository;
    private final CarteiraItemRepository itemRepository;

    public CarteiraService(CarteiraRepository repository, CarteiraItemRepository itemRepository) {
        this.repository = repository;
        this.itemRepository = itemRepository;
    }

    public Carteira salvar(Carteira carteira) {
        return repository.save(carteira);
    }

    public List<Carteira> listarPorUsuario(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    public Optional<Carteira> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public List<SugestaoInvestimento> calcularRebalanceamento(Long carteiraId, BigDecimal valorAporte) {

        // busca os itens da carteira
        List<CarteiraItem> itens = itemRepository.findByCarteiraId(carteiraId);
        List<SugestaoInvestimento> sugestoes = new ArrayList<>();

        // calcula quanto a carteira vale HOJE (Quantidade * Preço)
        BigDecimal patrimonioAtual = BigDecimal.ZERO;
        for (CarteiraItem item : itens) {
            BigDecimal valorItem = item.getAtivo().getCotacao()
                    .multiply(new BigDecimal(item.getQuantidade()));
            patrimonioAtual = patrimonioAtual.add(valorItem);
        }

        // soma o dinheiro novo (Aporte) para saber onde queremos chegar
        BigDecimal patrimonioProjetado = patrimonioAtual.add(valorAporte);

        // calcula o que comprar para cada ativo
        for (CarteiraItem item : itens) {
            // quanto eu deveria ter deste ativo? (Total Projetado * % Alvo / 100)
            BigDecimal valorMeta = patrimonioProjetado
                    .multiply(item.getPercentualAlvo())
                    .divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);

            // quanto eu já tenho?
            BigDecimal valorAtual = item.getAtivo().getCotacao()
                    .multiply(new BigDecimal(item.getQuantidade()));

            // qual a diferença (gap)?
            BigDecimal faltaComprar = valorMeta.subtract(valorAtual);

            // se falta comprar (valor positivo) e o preço da ação é maior que 0
            if (faltaComprar.compareTo(BigDecimal.ZERO) > 0 &&
                    item.getAtivo().getCotacao().compareTo(BigDecimal.ZERO) > 0) {

                // quantas ações cabem nesse valor? (Gap / Preço da Ação)
                // roundingMode.DOWN = Arredonda pra baixo (não dá pra comprar meia ação)
                BigDecimal qtdParaComprarDecimal = faltaComprar
                        .divide(item.getAtivo().getCotacao(), 0, RoundingMode.DOWN);

                int qtdParaComprar = qtdParaComprarDecimal.intValue();

                // só sugere se tiver que comprar pelo menos 1
                if (qtdParaComprar > 0) {
                    BigDecimal valorParaGastar = item.getAtivo().getCotacao()
                            .multiply(new BigDecimal(qtdParaComprar));

                    sugestoes.add(new SugestaoInvestimento(
                            item.getAtivo().getTicker(),
                            item.getQuantidade(),
                            qtdParaComprar,
                            valorParaGastar
                    ));
                }
            }
        }

        return sugestoes;
    }

    public void realizarOperacao(Long carteiraId, List<OrdemExecucao> ordens) {
        // busca todos os itens da carteira
        List<CarteiraItem> itens = itemRepository.findByCarteiraId(carteiraId);

        // para cada ordem de compra/venda que chegou...
        for (OrdemExecucao ordem : ordens) {

            // tenta encontrar o ativo correspondente na sua carteira
            Optional<CarteiraItem> itemExistente = itens.stream()
                    .filter(item -> item.getAtivo().getTicker().equalsIgnoreCase(ordem.getTicker()))
                    .findFirst();

            // se encontrou, atualiza a quantidade e salva
            if (itemExistente.isPresent()) {
                CarteiraItem item = itemExistente.get();
                // Nova Quantidade = Quantidade Atual + O que você mandou comprar
                item.setQuantidade(item.getQuantidade() + ordem.getQuantidade());
                itemRepository.save(item);
            }
        }
    }

    public Carteira atualizar(Long id, Carteira carteiraAtualizada) {
        return repository.findById(id).map(carteiraExistente -> {

            // Atualiza a descrição se ela foi enviada no JSON
            if (carteiraAtualizada.getDescricao() != null) {
                carteiraExistente.setDescricao(carteiraAtualizada.getDescricao());
            }
            return repository.save(carteiraExistente);
        }).orElseThrow(() -> new RuntimeException("Carteira não encontrada!"));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Carteira não encontrada!");
        }
        repository.deleteById(id);
    }

}