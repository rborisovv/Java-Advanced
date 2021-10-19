import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        var liquids = Arrays.stream(reader.readLine().split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(ArrayDeque::new));
        String[] ingredientsInput = reader.readLine().split("\\s+");
        ArrayDeque<Integer> ingredients = fillIngredients(ingredientsInput);

        var lookupTable = Map.of(
                "Biscuit", 25,
                "Cake", 50,
                "Pastry", 75,
                "Pie", 100
        );

        var resultTable = new LinkedHashMap<String, Integer>();
        resultTable.put("Biscuit", 0);
        resultTable.put("Cake", 0);
        resultTable.put("Pie", 0);
        resultTable.put("Pastry", 0);

        while (!liquids.isEmpty() && !ingredients.isEmpty()) {
            int liquid = liquids.poll();
            int ingredient = ingredients.pop();
            int productValue = liquid + ingredient;

            Optional<String> optionalProduct = findProductIfPresent(lookupTable, productValue);
            if (optionalProduct.isPresent()) {
                String productName = optionalProduct.get();
                resultTable.put(productName, resultTable.get(productName) + 1);
            } else {
                ingredients.push(ingredient + 3);
            }
        }
        boolean allFoodIsPresent = assertAllFoodIsPresent(resultTable);
        StringBuilder result = new StringBuilder();
        result.append(allFoodIsPresent ? "Great! You succeeded in cooking all the food!" :
                        "What a pity! You didn't have enough materials to cook everything.")
                .append(System.lineSeparator());

        appendElementsLeft(liquids, "Liquids", result);
        appendElementsLeft(ingredients, "Ingredients", result);
        resultTable.forEach((key, value) -> result.append(String.format("%s: %d", key, value)).append(System.lineSeparator()));
        System.out.println(result.toString().trim());
    }

    private static void appendElementsLeft(ArrayDeque<Integer> arrayDeque, String element, StringBuilder result) {
        if (arrayDeque.isEmpty()) {
            result.append(String.format("%s left: none", element)).append(System.lineSeparator());
        } else {
            result.append(String.format("%s left: ", element));
            result.append(arrayDeque.stream().map(String::valueOf).collect(Collectors.joining(", "))).append(System.lineSeparator());
        }
    }

    private static boolean assertAllFoodIsPresent(LinkedHashMap<String, Integer> resultTable) {
        return resultTable.values().stream().noneMatch(v -> v == 0);
    }

    private static ArrayDeque<Integer> fillIngredients(String[] ingredients) {
        ArrayDeque<Integer> tempStack = new ArrayDeque<>();
        Arrays.stream(ingredients).map(Integer::parseInt).forEach(tempStack::push);
        return tempStack;
    }

    private static Optional<String> findProductIfPresent(Map<String, Integer> lookupTable, int productValue) {
        return lookupTable.entrySet().stream().filter((e) -> e.getValue() == productValue)
                .map(Map.Entry::getKey)
                .findFirst();
    }
}