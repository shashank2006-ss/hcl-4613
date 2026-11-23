package assignment2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(AppConfig.class)) {
            Student firstStudent = context.getBean(Student.class);
            Student secondStudent = context.getBean(Student.class);

            System.out.println("First student bean ID: " + System.identityHashCode(firstStudent));
            System.out.println("Second student bean ID: " + System.identityHashCode(secondStudent));
            firstStudent.notifyAssessmentScheduled();
        }
    }
}
