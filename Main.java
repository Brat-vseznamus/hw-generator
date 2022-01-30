import java.util.Random;

import generation.Expression;
import generation.TexElement.Document;

import static generation.TaskGenerator.*;
import static generation.Expression.*;


public class Main {

    private static Random random = new Random();
    public static void main(String[] args) {
        generate(args[0], createDocument());
    }

    private static Document createDocument() {
        return 
            document(
                task(
                    "Задание 1", 
                    "В каждом номере этого задания надо посчитать " + 
                    "дробь и написать итоговый результат, сократив " + 
                    "числитель и знаменатель до взаимной простоты.", 
                    table(
                        "Сложение", 3, 3, 
                        () -> simpleExpr(0)
                    ),
                    table(
                        "Вычитание", 3, 3, 
                        () -> simpleExpr(1)
                    ),
                    table(
                        "Умножение", 3, 3, 
                        () -> simpleExpr(2)
                    )
                ),
                task(
                    "Задание 2", 
                    "В каждом номере этого задания надо посчитать " + 
                    "дробь и написать итоговый результат, сократив " + 
                    "числитель и знаменатель до взаимной простоты.", 
                    table(
                        "Номера", 10, 1, 
                        () -> hardExpr(2)
                    )
                ),
                task(
                    "Задание 3", 
                    "В каждом номере этого задания надо посчитать " + 
                    "дробь и написать итоговый результат, сократив " + 
                    "числитель и знаменатель до взаимной простоты.", 
                    table(
                        "Номера", 5, 1, 
                        () -> hardExpr(3)
                    )
                )
            );
    }

    private static Expression simpleExpr(int caseNum) {
        Expression l = randomFraction();
        Expression r = randomFraction();
        switch (caseNum) {
            case 0: return new Add(l, r);
            case 1: return new Subtract(l, r);
            case 2: return new Multiply(l, r);
            default: return new Divide(l, r);
        }
    }

    private static Expression randomFraction() {
        int a = random.nextInt(50) + 1;
        int b = random.nextInt(50) + 1;
        return new Divide(
            new Expression.Number(a), 
            new Expression.Number(b));
    }

    private static Expression hardExpr(int level) {
        if (level <= 0) {
            return randomFraction();
        }
        Expression l = hardExpr(level - 1);
        Expression r = hardExpr(level - 1);

        boolean lInBrackets = random.nextInt(2) == 1;
        boolean rInBrackets = random.nextInt(2) == 1;
        
        if (lInBrackets) l = new Brackets(l);
        if (rInBrackets) r = new Brackets(r);

        int caseNum = random.nextInt(7);

        switch (caseNum) {
            case 0: 
            case 1: return new Add(l, r);
            case 2:
            case 3: return new Subtract(l, r);
            case 4:
            case 5: return new Multiply(l, r);
            case 6: return new Divide(l, r);
            default: return null;
        }
    }
}
