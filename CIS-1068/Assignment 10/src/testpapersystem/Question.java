package testpapersystem;

abstract class Question {
    protected int points;
    protected int difficulty;
    protected int answerSpace;
    protected String questionText;

    public Question(int points, int difficulty, int answerSpace, String questionText) {
        this.points = points;
        this.difficulty = difficulty;
        this.answerSpace = answerSpace;
        this.questionText = questionText;
    }

    public abstract String toString();
    public abstract String toAnswerString();
    public abstract String toFileString();

}


