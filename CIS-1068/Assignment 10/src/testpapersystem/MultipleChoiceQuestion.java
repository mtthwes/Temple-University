package testpapersystem;

import java.util.Arrays;
import java.util.List;

class MultipleChoiceQuestion extends Question {
    private List<String> possibleAnswers;
    private String correctAnswer;

    public MultipleChoiceQuestion(int points, int difficulty, int answerSpace, String questionText, List<String> possibleAnswers, String correctAnswer) {
        super(points, difficulty, answerSpace, questionText);
        this.possibleAnswers = possibleAnswers;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        StringBuilder options = new StringBuilder();
        for (String option : possibleAnswers) {
            options.append(option).append("\n");
        }
        return questionText + "\n" + options.toString();
    }

    @Override
    public String toAnswerString() {
        StringBuilder options = new StringBuilder();
        for (String option : possibleAnswers) {
            if (option.equals(correctAnswer)) {
                options.append("**** ").append(option).append(" ****\n");
            } else {
                options.append(option).append("\n");
            }
        }
        return questionText + "\n" + options.toString();
    }
    @Override
    public String toFileString() {
        return "MultipleChoiceQuestion|" + points + "|" + difficulty + "|" + answerSpace + "|" + questionText + "|" + String.join(";", possibleAnswers) + "|" + correctAnswer;
    }

    public static MultipleChoiceQuestion fromFileString(String data) {
        String[] parts = data.split("\\|");
        List<String> options = Arrays.asList(parts[5].split(";"));
        return new MultipleChoiceQuestion(
            Integer.parseInt(parts[1]),
            Integer.parseInt(parts[2]),
            Integer.parseInt(parts[3]),
            parts[4],
            options,
            parts[6]
        );
    }

}

