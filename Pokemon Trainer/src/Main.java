import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        String[] tokens = reader.readLine().split("\\s+");
        List<Trainer> trainers = new ArrayList<>();

        while (!tokens[0].equals("Tournament")) {
            String trainerName = tokens[0];
            String pokemonName = tokens[1];
            String pokemonElement = tokens[2];
            Integer pokemonHealth = Integer.parseInt(tokens[3]);

            Trainer trainer = new Trainer(trainerName);
            Pokemon pokemon = new Pokemon(pokemonName, pokemonElement, pokemonHealth);
            if (assertTrainerIsPresent(trainers, trainerName)) {
                Trainer foundTrainer = trainers.stream().filter(t -> t.getName().equals(trainerName)).findFirst().orElse(null);
                assert foundTrainer != null;
                foundTrainer.getPokemons().add(pokemon);
            } else {
                trainer.getPokemons().add(pokemon);
                trainers.add(trainer);
            }
            tokens = reader.readLine().split("\\s+");
        }

        String element = reader.readLine();
        while (!element.equals("End")) {

            for (Trainer trainer : trainers) {
                if (assertTrainerHasPokemon(trainer, element)) {
                    trainer.setBadges(trainer.getBadges() + 1);
                } else {
                    trainer.getPokemons().forEach(p -> p.setHealth(p.getHealth() - 10));
                    trainer.setPokemons(trainer.getPokemons().stream().filter(p -> p.getHealth() > 0).collect(Collectors.toList()));
                }
            }
            element = reader.readLine();
        }
        trainers.stream().sorted(Comparator.comparing(Trainer::getBadges).reversed()).forEach(System.out::println);
    }

    private static boolean assertTrainerHasPokemon(Trainer trainer, String element) {
        return trainer.getPokemons().stream().anyMatch(p -> p.getElement().equals(element));
    }

    private static boolean assertTrainerIsPresent(List<Trainer> trainers, String trainerName) {
        return trainers.stream().anyMatch(t -> t.getName().equals(trainerName));
    }
}