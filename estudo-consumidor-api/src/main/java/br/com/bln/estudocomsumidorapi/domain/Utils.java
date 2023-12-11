package br.com.bln.estudocomsumidorapi.domain;

public class Utils {
    public static int gerarTempoDeTrabalhoAleatorio(){
        return 5000;
    }

    public static void trabalhar(int tempoDeTrabalho) throws InterruptedException {
        Thread.sleep(tempoDeTrabalho);
    }
}
