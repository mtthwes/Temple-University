package testpapersystem;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

class Test {
    private List<Question> questions;
    private int totalPoints;

    public Test(List<Question> questions) {
        this.questions = questions;
        this.totalPoints = questions.stream().mapToInt(q -> q.points).sum();
    }

    @Override
    public String toString() {
        StringBuilder testString = new StringBuilder();
        for (Question question : questions) {
            testString.append(question.toString()).append("\n");
        }
        testString.append("Total Points: ").append(totalPoints).append("\n");
        return testString.toString();
    }

    public String toAnswerString() {
        StringBuilder answerKeyString = new StringBuilder();
        for (Question question : questions) {
            answerKeyString.append(question.toAnswerString()).append("\n");
        }
        return answerKeyString.toString();
    }
    public void saveToFile(String testFileName, String answerKeyFileName) throws IOException {
        try (FileWriter testWriter = new FileWriter(testFileName); 
             FileWriter answerKeyWriter = new FileWriter(answerKeyFileName)) {
            testWriter.write(this.toString());
            answerKeyWriter.write(this.toAnswerString());
        }
    }
}


