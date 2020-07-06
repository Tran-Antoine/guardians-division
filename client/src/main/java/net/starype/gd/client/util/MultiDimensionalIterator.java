package net.starype.gd.client.util;

import com.jme3.math.Vector3f;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class MultiDimensionalIterator {

    private Iteration top = null;
    private List<Consumer<List<Float>>> actions;
    private Set<Predicate<List<Float>>> filters;
    private List<Float> shift;

    public MultiDimensionalIterator() {
        this.actions = new ArrayList<>();
        this.filters = new HashSet<>();
        this.shift = new ArrayList<>();
    }

    public void queueIteration(float from, float to, float step) {
        queueIteration(from, to, step, 1);
    }

    public void queueIteration(float from, float to, float step, int times) {
        if(times > 1) {
            queueIteration(from, to, step, times - 1);
        }

        if(top == null) {
            top = new Iteration(null, from, to, step);
            return;
        }
        top.stack(from, to, step);
    }

    public void addAction(Consumer<List<Float>> action) {
        actions.add(action);
    }

    public void addFilter(Predicate<List<Float>> condition) {
        filters.add(condition);
    }

    public void shift(List<Float> shift) {
        this.shift = shift;
    }

    public void run() {

        boolean canContinue = true;

        while(canContinue) {
            List<Float> values = new ArrayList<>();
            canContinue = top.nextIteration(values);
            shiftValues(values);
            for(Consumer<List<Float>> action : actions) {
                if(filters
                        .stream()
                        .allMatch(listPredicate -> listPredicate.test(values))) {

                    action.accept(values);
                }
            }
        }
    }

    private void shiftValues(List<Float> values) {
        for(int i = 0; i < shift.size(); i++) {
            values.set(i, values.get(i) + shift.get(i));
        }
    }

    public static List<Float> asShift(Vector3f vec) {
        return Arrays.asList(vec.x, vec.y, vec.z);
    }

    private static class Iteration {

        private float from;
        private float to;
        private float step;
        private float current;

        private Iteration parent;
        private Iteration next;

        public Iteration(Iteration parent, float from, float to, float step) {
            this.parent = parent;
            this.from = from;
            this.to = to;
            this.step = step;
            this.current = from;
        }

        private void stack(float from, float to, float step) {
            if(next == null) {
                this.next = new Iteration(this, from, to, step);
            } else {
                next.stack(from, to, step);
            }
        }

        private void increment() {
            current += step;
        }

        private Iteration onLoopEnded() {
            if(parent != null) {
                parent.increment();
                if(parent.wentOverEnd()) {
                    return parent.onLoopEnded();
                }
                return this;
            }
            return null;
        }

        private void reset() {
            this.current = from;
            if(next != null) {
                next.reset();
            }
        }

        private boolean wentOverEnd() {
            return current > to;
        }

        private boolean nextIteration(List<Float> toFill) {
            toFill.add(current);

            if(next == null) {
                increment();
            }
            if(wentOverEnd()) {
                Iteration root = onLoopEnded();
                if(root == null) { // meaning the retrieved iteration is the head -> looping should be terminated
                    return false;
                }
                root.reset();
                return true;
            }
            if(next != null) return next.nextIteration(toFill);
            return true;
        }
    }

    public static class Builder {

        private MultiDimensionalIterator scanner;

        public Builder() {
            this.scanner = new MultiDimensionalIterator();
        }

        public MultiDimensionalIterator build() {
            return scanner;
        }

        public Builder queueIteration(float from, float to, float step) {
            scanner.queueIteration(from, to, step);
            return this;
        }

        public Builder shift(List<Float> shift) {
            scanner.shift(shift);
            return this;
        }

        public Builder withAction(Consumer<List<Float>> action) {
            scanner.addAction(action);
            return this;
        }

        public Builder withFilter(Predicate<List<Float>> condition) {
            scanner.addFilter(condition);
            return this;
        }
    }
}