package testpapersystem;

class FillInTheBlankQuestion extends Question {
    private String correctAnswer;

    public FillInTheBlankQuestion(int points, int difficulty, int answerSpace, String questionText, String correctAnswer) {
        super(points, difficulty, answerSpace, questionText);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        return questionText.replace("___", "______") + "\n";
    }

    @Override
    public String toAnswerString() {
        return questionText.replace("___", "___" + correctAnswer + "___") + "\n";
    }
    
    @Override
    public String toFileString() {
        return "FillInTheBlankQuestion|" + points + "|" + difficulty + "|" + answerSpace + "|" + questionText + "|" + correctAnswer;
    }

    public static FillInTheBlankQuestion fromFileString(String data) {
        String[] parts = data.split("\\|");
        return new FillInTheBlankQuestion(
            Integer.parseInt(parts[1]),
            Integer.parseInt(parts[2]),
            Integer.parseInt(parts[3]),
            parts[4],
            parts[5]
        );
    }

}
