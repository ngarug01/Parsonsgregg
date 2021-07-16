package com.ten10.training.javaparsons.impl.ExerciseCheckers;

import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.impl.ClassChecker;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Optional;

public class StaticFieldValueChecker implements ClassChecker {
    private final String desc;
    private final String goal;
    private final Object answer;

    public StaticFieldValueChecker(String desc, String goal, Object answer) {
        this.desc = desc;
        this.goal = goal;
        this.answer = answer;
    }

    public String getDesc() {
        return desc;
    }

    Optional<Field> findField(Field[] fields) {
        {
            for (Field field : fields) {
                if (field.getName().equals(goal)) {
                    return Optional.of(field);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Boolean validate(Field[] klassFields, ProgressReporter progressReporter) {
        {

            Optional<Field> optionalField = findField(klassFields);
            if (!optionalField.isPresent()) {
                progressReporter.reportRunnerError("Either your variables have incorrect access modifiers and/or names, or one isn't there!");
                return false;
            }
            Field field = optionalField.get();
            try {
                field.setAccessible(true);
                if (!Modifier.isStatic(field.getModifiers())) {
                    progressReporter.reportRunnerError("your variable(s) need to be static!");
                    return false;
                }
                if (!field.get(null).equals(answer)) {
                    progressReporter.reportRunnerError("The " + field.getName() + " variable has not been initialised correctly!");
                    return false;
                }
            } catch (IllegalArgumentException e) {
                progressReporter.reportRunnerError("your variable(s) need to be static!");
                return false;
            } catch (IllegalAccessException e) {
                progressReporter.reportRunnerError("No access to a field");
                return false;
            }
        }
        return true;
    }
}

