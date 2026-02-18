package testpapersystem;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class TestBank {
    private List<Question> questions;
    
    public TestBank() {
        questions = new ArrayList<>();
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public Test generateTest(int numQuestions) {
        Collections.shuffle(questions);
        List<Question> selectedQuestions = questions.subList(0, Math.min(numQuestions, questions.size()));
        return new Test(new ArrayList<>(selectedQuestions));
    }

    public void saveToFile(String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (Question question : questions) {
                writer.write(question.toFileString() + "\n");
            }
        }
    }
    
    public void loadFromFile(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                switch (parts[0]) {
                    case "ObjectiveQuestion":
                        questions.add(ObjectiveQuestion.fromFileString(line));
                        break;
                    case "FillInTheBlankQuestion":
                        questions.add(FillInTheBlankQuestion.fromFileString(line));
                        break;
                    case "MultipleChoiceQuestion":
                        questions.add(MultipleChoiceQuestion.fromFileString(line));
                        break;
                }
            }
        }
    }

}