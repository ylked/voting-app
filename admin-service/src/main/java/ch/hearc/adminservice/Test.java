package ch.hearc.adminservice;

import java.util.stream.IntStream;

public class Test {

    public static void main(String[] args) {
        int nbVotes = 100;

        IntStream.range(0,nbVotes).forEach(it -> {
            System.out.println(it);
        });

    }
}
