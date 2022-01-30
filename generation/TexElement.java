package generation;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class TexElement {

    public abstract List<String> texLines();

    public void write(BufferedWriter writer) throws IOException {
        for (String line : texLines()) {
            writer.write(line);
            writer.write("\n");
        }
    }

    public static class Section extends TexElement {
        String name;
        
        public Section(String name) {
            this.name = name;
        }

        @Override
        public List<String> texLines() {
            return List.of(String.format("\\section*{%s}", name));
        }

    }

    public static class Text extends TexElement {
        String text;
        
        public Text(String text) {
            this.text = text;
        }

        @Override
        public List<String> texLines() {
            return List.of(text);
        }
    }

    public static class Table extends TexElement {
        List<Row> rows;

        public Table(Row... rows) {
            this.rows = new ArrayList<>(Arrays.asList(rows));
        }

        private static int taskNumber = 1;

        public static class Row {
            List<Expression> expressions;

            public Row(Expression... expressions) {
                this.expressions = new ArrayList<>(Arrays.asList(expressions));
            }

            public String toTex() {
                return expressions.stream()
                    .map(Expression::toTex)
                    .map(s -> (taskNumber++) + ") {\\Large $" + s + "=$}")
                    .collect(Collectors.joining(" & "));
            }
        }

        public void add(Row element) {
            rows.add(element);
        }

        @Override
        public List<String> texLines() {
            List<String> lines = new ArrayList<>();
            lines.add("\\begin{tabular*}{\\textwidth}{l @{\\extracolsep{\\fill}} lllll}");
            lines.addAll(rows.stream()
                .map(Row::toTex)
                .map(s -> "\t" + s + "\\\\\t\\\\")
                .collect(Collectors.toList()));
            taskNumber = 1;
            lines.add("\\end{tabular*}");
            return lines;
        }
    }

    public static class Block extends TexElement {
        List<TexElement> elements;

        public Block(TexElement... elements) {
            this.elements = new ArrayList<>(Arrays.asList(elements));
        }

        public void add(TexElement element) {
            elements.add(element);
        }

        @Override
        public List<String> texLines() {
            return elements.stream()
                .map(TexElement::texLines)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        }
    }

    public static class Document extends TexElement {
        List<TexElement> elements;

        public Document(TexElement... elements) {
            this.elements = new ArrayList<>(Arrays.asList(elements));
        }

        public void add(TexElement element) {
            elements.add(element);
        }

        @Override
        public List<String> texLines() {
            List<String> lines = new ArrayList<String>();
            lines.addAll(List.of(
                "\\documentclass[12pt, a4paper]{article}",
                "",
                "\\usepackage{amsmath}",
                "\\usepackage{amssymb}",
                "\\usepackage{amsthm}",
                "\\usepackage[utf8]{inputenc}",
                "\\usepackage[russian, english]{babel}",
                "\\usepackage{mathtext}",
                "\\usepackage[T1,T2A]{fontenc}"
            ));
            lines.add("\\begin{document}");
            lines.addAll(elements.stream()
                .map(TexElement::texLines)
                .flatMap(List::stream)
                .map(s -> "\t" + s)
                .collect(Collectors.toList()));
            lines.add("\\end{document}");
            return lines;
        }
    }

    public static TexElement text(String text) {
        return new TexElement.Text(text);
    }

    public static TexElement newLine() {
        return text("\\hfill \\break");
    }

}
