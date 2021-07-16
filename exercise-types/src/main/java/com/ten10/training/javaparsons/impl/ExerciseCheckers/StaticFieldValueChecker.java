package com.ten10.training.javaparsons.impl.ExerciseCheckers;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.impl.ClassChecker;

import java.lang.reflect.Field;
import java.util.NoSuchElementException;
import java.util.Optional;

public class StaticFieldValueChecker implements ClassChecker {
    private final String desc;
    private final String goal;
    private final Object answer;
    private final String secondgoal;
    private final Object secondanswer;

    public StaticFieldValueChecker(String desc, String goal, Object answer, String secondgoal, Object secondanswer) {
        this.desc = desc;
        this.goal = goal;
        this.answer = answer;
        this.secondgoal = secondgoal;
        this.secondanswer = secondanswer;
    }

    public String getDesc() {
        return desc;
    }

    Optional<Field> findFirstField(Field[] fields) {
        {
            for (Field field : fields) {
                if (field.getName().equals(goal)) {
                    return Optional.of(field);
                }
            }
        }
        return Optional.empty();
    }

    Optional<Field> findSecondField(Field[] fields) {
        {
            for (Field field : fields) {
                if (field.getName().equals(secondgoal)) {
                    return Optional.of(field);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Boolean validate(Field[] klassFields, ProgressReporter progressReporter) {
        {
            try {
                Field FirstField = findFirstField(klassFields).get();
                Field SecondField = findSecondField(klassFields).get();
                SecondField.setAccessible(true);
                FirstField.setAccessible(true);
                if (!FirstField.get(FirstField).equals(answer)) {
                    progressReporter.reportRunnerError("The " + FirstField.getName() + " variable has not been initialised correctly!");
                    return false;
                }
                if (!SecondField.get(SecondField).equals(secondanswer)) {
                    progressReporter.reportRunnerError("The" + SecondField.getName() + "variable has not been initialised correctly!");
                    return false;
                } else {
                    return true;
                }
            } catch (NoSuchElementException e) {
                progressReporter.reportRunnerError("Either your variables have incorrect access modifiers and/or names, or one isn't there!");
            } catch (IllegalArgumentException e) {
                progressReporter.reportRunnerError("your variable(s) need to be static!");
            } catch (IllegalAccessException e) {
                progressReporter.reportRunnerError("No access to a field");
            }
        }
        return false;
    }
}

