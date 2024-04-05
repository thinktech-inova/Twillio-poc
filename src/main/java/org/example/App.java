package org.example;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App
{
    private static final String ACCOUNT_SID = "****";
    private static final String AUTH_TOKEN = "****";

    public static void main( String[] args )
    {
        // configuração das credenciais do Twilio
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // números testes para envio de mensagem e templates
        List<String> numerosTestes = Arrays.asList("11962123215","11989207944");

        String nomeArquivo = "src/main/resources/numeros_celular_1.txt";
        List<String> numerosCelularOficial = lerNumerosCelular(nomeArquivo);

        if (numerosCelularOficial != null) {
            System.out.println("NÚMEROS DE CELULAR LIDOS COM SUCESSO");
            envioWhatsapp(numerosTestes);
        } else {
            System.out.println("ERRO AO LER OS NÚMEROS DE CELULAR");
        }
    }

    private static void envioWhatsapp(List<String> numerosCelular) {

        String numeroCelular;
        int contador = 0;

        // Envio da mensagem usando API do Twilio
        for (String numero : numerosCelular) {
            numeroCelular = "whatsapp:+55" + numero;
            Message message = Message.creator(
                            new com.twilio.type.PhoneNumber(numeroCelular),
                            new com.twilio.type.PhoneNumber("MGf948a1428b9d2fd6dfac30fde349a0bc"),
                            "Convite especial - Comunicado")
                    .setContentSid("HX236bb17e8c04326a070a9310cde30287")
                    .create();

            System.out.println("Erro: " + message.getErrorMessage());
            System.out.println("mensagem enviada: " + ++contador);

            try {
                Thread.sleep(20000); // Atraso de 20 segundos (em milissegundos)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("FINALIZADO ENVIO DAS MENSAGENS");
    }

    private static List<String> lerNumerosCelular(String nomeArquivo) {
        List<String> numerosCelular = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] numeros = linha.split(",");
                for (String numero : numeros) {
                    numerosCelular.add(numero.trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return numerosCelular;
    }
}
