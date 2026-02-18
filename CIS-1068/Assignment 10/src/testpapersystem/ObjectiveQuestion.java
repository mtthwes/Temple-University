package testpapersystem;

class ObjectiveQuestion extends Question {
    private String correctAnswer;

    public ObjectiveQuestion(int points, int difficulty, int answerSpace, String questionText, String correctAnswer) {
        super(points, difficulty, answerSpace, questionText);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        return questionText + "\n" + "_".repeat(answerSpace) + "\n";
    }

    @Override
    public String toAnswerString() {
        return questionText + "\nCorrect Answer: " + correctAnswer + "\n";
    }
    
    @Override
    public String toFileString() {
        return "ObjectiveQuestion|" + points + "|" + difficulty + "|" + answerSpace + "|" + questionText + "|" + correctAnswer;
    }

    public static ObjectiveQuestion fromFileString(String data) {
        String[] parts = data.split("\\|");
        return new ObjectiveQuestion(
            Integer.parseInt(parts[1]),
            Integer.parseInt(parts[2]),
            Integer.parseInt(parts[3]),
            parts[4],
            parts[5]
        );
    }

}
