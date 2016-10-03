package com.example.student.mariocrystalball;

public class Predictions {

    private static Predictions predictions;
    private String[] answers;

    private Predictions() {
        answers = new String[] {
                "Why would you ever ask that?",
                "Absolutely!",
                "Please ask when I am not tired.",
                "Change your ways.",
                "Of course not!",
                "A great burden has fallen on your shoulders...",
                "Woo-Hoo!",
                "Your Princess is in another castle.",
                "Mamma Mia!"
        };
    }

    public static Predictions get() {
        if(predictions == null) {
            predictions = new Predictions();
        }

        return predictions;
    }

    public String getPrediction() {
        return answers[5];
    }

}
