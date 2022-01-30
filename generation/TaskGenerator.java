package generation;

import static generation.TexElement.*;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TaskGenerator {

    private TaskGenerator() {}

    public static void generate(String name, Document d) {
        Path path = Path.of(name + ".tex");
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            d.write(writer);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static Document document(
        Block...blocks
    ) {
        Document d = new Document();

        Arrays.asList(blocks).forEach(d::add);

        return d;
    }

    public static Block task(
        String name,
        String description,
        Block... blocks
    ) {
        Block result = new Block();

        result.add(new Section(name));
        result.add(new Text(description));
        result.add(newLine());
        result.add(newLine());
        
        Arrays.asList(blocks).forEach(result::add);

        return result;
    }

    public static Block table(
        String description, 
        int nOfRows, 
        int nOfCols, 
        Supplier<Expression> exprGenerator) {

        Block block = new Block();

        block.add(text(description));
        block.add(newLine());
        block.add(newLine());

        Table t = new Table();

        IntStream.range(0, nOfRows)
            .forEach(j -> t.add(generateRow(nOfCols, exprGenerator)));
        
        block.add(t);
        block.add(newLine());

        return block;
    }


    private static TexElement.Table.Row generateRow(
        int size, 
        Supplier<Expression> exprGenerator) {
        Expression[] exprs = new Expression[size];

        return new TexElement.Table.Row(
            IntStream.range(0, size)
            .mapToObj(i -> exprGenerator.get())
            .collect(Collectors.toList())
            .toArray(exprs));
    }
}