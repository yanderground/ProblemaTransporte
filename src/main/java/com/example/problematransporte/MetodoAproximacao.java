package com.example.problematransporte;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MetodoAproximacao {

    private ArrayList<ArrayList<Double>> tabela;
    private ArrayList<Double> oferta;
    private ArrayList<String> listaOrigens;
    private ArrayList<Double> penalidadeOferta;
    private ArrayList<Double> demanda;
    private ArrayList<Double> penalidadeDemanda;
    private ArrayList<String> listaDestinos;
    private ArrayList<String> resposta;

    public MetodoAproximacao(ArrayList<ArrayList<Double>> tabela, ArrayList<Double> oferta, ArrayList<Double> demanda) {
        this.resposta = new ArrayList<>();
        this.tabela = new ArrayList<>(tabela);
        this.oferta = new ArrayList<>(oferta);
        atualizarListaOrigens();
        this.demanda = new ArrayList<>(demanda);
        atualizarListaDestinos();
    }

    public void calcular(boolean descrever) {
        imprimirTabelaAtual();
        while (tabela.size() != 1 && demanda.size() != 1) {
            calcularPenalidadeOferta();
            calcularPenalidadeDemanda();
            ArrayList<Integer> caminho = escolherCaminho();
            atualizarTabela(caminho.get(0), caminho.get(1));

            if (descrever) {
                imprimirTabelaAtual();
            }
        }

        if (tabela.size() == 1) {
            relacionarOrigemDestinoPorLinha();
        }

        if (demanda.size() == 1) {
            relacionarOrigemDestinoPorColuna();
        }
    }

    private void calcularPenalidadeOferta() {
        penalidadeOferta = new ArrayList<>();
        for (ArrayList<Double> linha : tabela) {
            Double melhorPrimeiro = Collections.min(linha);
            ArrayList<Double> copiaLinha = new ArrayList<>(linha);
            copiaLinha.remove(melhorPrimeiro);
            Double segundoMelhor = Collections.min(copiaLinha);
            penalidadeOferta.add(segundoMelhor - melhorPrimeiro);
        }
    }

    private void calcularPenalidadeDemanda() {
        penalidadeDemanda = new ArrayList<>();
        for (int i = 0; i < tabela.get(0).size(); i++) {
            ArrayList<Double> coluna = getColunaPorIndice(i);
            Double melhorPrimeiro = Collections.min(coluna);
            ArrayList<Double> copiaColuna = new ArrayList<>(coluna);
            copiaColuna.remove(melhorPrimeiro);
            Double segundoMelhor = Collections.min(copiaColuna);
            penalidadeDemanda.add(segundoMelhor - melhorPrimeiro);
        }
    }

    private void atualizarListaOrigens() {
        listaOrigens = new ArrayList<>();
        for (int i = 0; i < oferta.size(); i++) {
            listaOrigens.add("Origem " + (i + 1));
        }
    }

    private void atualizarListaDestinos() {
        listaDestinos = new ArrayList<>();
        for (int i = 0; i < demanda.size(); i++) {
            listaDestinos.add("Destino " + (i + 1));
        }
    }

    private ArrayList<Integer> escolherCaminho() {
        int cY;
        int cX;
        Double maiorValorPenalidadeOferta = Collections.max(penalidadeOferta);
        int indiceMaiorOfertaPenalidade = penalidadeOferta.indexOf(maiorValorPenalidadeOferta);

        Double maiorValorPenalidadeDemanda = Collections.max(penalidadeDemanda);
        int indiceMaiorDemandaPenalidade = penalidadeDemanda.indexOf(maiorValorPenalidadeDemanda);

        if (maiorValorPenalidadeOferta > maiorValorPenalidadeDemanda) {
            cY = indiceMaiorOfertaPenalidade;
            Double min = Collections.min(tabela.get(cY));
            cX = tabela.get(cY).indexOf(min);
        } else {
            cX = indiceMaiorDemandaPenalidade;
            ArrayList<Double> coluna = getColunaPorIndice(cX);
            Double min = Collections.min(coluna);
            cY = coluna.indexOf(min);
        }
        return new ArrayList<>(List.of(cX, cY));
    }

    private void atualizarTabela(Integer cX, Integer cY) {
        Double demandaLocal = demanda.get(cX);
        Double ofertaLocal = oferta.get(cY);

        Double quantidadeTransportada = Math.min(ofertaLocal, demandaLocal);

        Double demandaAtualizada = demandaLocal - quantidadeTransportada;
        Double ofertaAtualizada = ofertaLocal - quantidadeTransportada;

        demanda.set(cX, demandaAtualizada);
        oferta.set(cY, ofertaAtualizada);

        resposta.add("Quantidade: " + quantidadeTransportada + " | " + listaOrigens.get(cY) + " -> " + listaDestinos.get(cX));

        if (demandaAtualizada == 0) {
            removerColunaPorIndice(cX);
            listaDestinos.remove(cX);
        }

        if (ofertaAtualizada == 0) {
            removerLinhaPorIndice(cY);
            listaOrigens.remove(cY);
        }
    }

    private void relacionarOrigemDestinoPorLinha() {
        ArrayList<Double> ultimaLinha = tabela.get(0);
        for (int i = 0; i < ultimaLinha.size(); i++) {
            resposta.add("Quantidade: " + demanda.get(i) + " | " + listaOrigens.get(0) + " -> " + listaDestinos.get(i));
        }
    }

    private void relacionarOrigemDestinoPorColuna() {
        ArrayList<Double> ultimaColuna = getColunaPorIndice(0);
        for (int i = 0; i < ultimaColuna.size(); i++) {
            resposta.add("Quantidade: " + oferta.get(i) + " | " + listaOrigens.get(i) + " -> " + listaDestinos.get(0));
        }
    }

    private ArrayList<Double> getColunaPorIndice(Integer indiceColuna) {
        ArrayList<Double> coluna = new ArrayList<>();
        for (ArrayList<Double> linha : tabela) {
            coluna.add(linha.get(indiceColuna));
        }
        return coluna;
    }

    private void removerColunaPorIndice(Integer cX) {
        demanda.remove((int) cX);
        listaDestinos.remove((int) cX);
        for (ArrayList<Double> linha : tabela) {
            linha.remove((int) cX);
        }
    }

    private void removerLinhaPorIndice(Integer linhaIndice) {
        oferta.remove((int) linhaIndice);
        listaOrigens.remove((int) linhaIndice);
        tabela.remove((int) linhaIndice);
    }

    // Métodos de acesso

    public ArrayList<ArrayList<Double>> getTabela() {
        return new ArrayList<>(tabela);
    }

    public ArrayList<Double> getOferta() {
        return new ArrayList<>(oferta);
    }

    public ArrayList<Double> getPenalidadeOferta() {
        return new ArrayList<>(penalidadeOferta);
    }

    public ArrayList<Double> getDemanda() {
        return new ArrayList<>(demanda);
    }

    public ArrayList<Double> getPenalidadeDemanda() {
        return new ArrayList<>(penalidadeDemanda);
    }

    public ArrayList<String> getResposta() {
        return new ArrayList<>(resposta);
    }

    public ArrayList<String> getListaOrigens() {
        return new ArrayList<>(listaOrigens);
    }

    public ArrayList<String> getListaDestinos() {
        return new ArrayList<>(listaDestinos);
    }

    public void imprimirTabelaAtual() {
        String imprimir = "";
        for (int contagemY = 0; contagemY < this.tabela.size(); contagemY++) {
            imprimir = imprimir.concat(this.listaOrigens.get(contagemY) + " | ");
            ArrayList<Double> linha = this.tabela.get(contagemY);
            for (int contagemX = 0; contagemX < linha.size(); contagemX++) {
                imprimir = imprimir.concat(linha.get(contagemX) + " | ");
            }
            imprimir = imprimir.concat(this.oferta.get(contagemY) + " | \n");
        }

        for (int contagemX = 0; contagemX < this.demanda.size(); contagemX++) {
            imprimir = imprimir.concat(this.demanda.get(contagemX) + " | ");
        }

        for (int contagemX = 0; contagemX < this.listaDestinos.size(); contagemX++) {
            imprimir = imprimir.concat(this.listaDestinos.get(contagemX) + " | ");
        }
        System.out.println(imprimir);
        System.out.println("\n ********************************************************************** \n");

    }

    public void imprimirRespostas() {
        for (String resposta : this.resposta) {
            System.out.println(resposta);
        }
    }
}

