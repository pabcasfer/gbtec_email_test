package com.gbtec.utils.filters;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.Consumer;

public class FilterField<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -3998730511594421804L;

    @Getter
    private final T value;

    private final boolean isPresent;

    private FilterField() {
        this.value = null;
        this.isPresent = false;
    }

    private FilterField(T value) {
        this.value = value;
        this.isPresent = true;
    }

    public static <T> FilterField<T> empty() {
        return new FilterField<>();
    }

    public static <T> FilterField<T> of(T value) {
        return new FilterField<>(value);
    }

    public boolean isPresent() {
        return this.isPresent;
    }

    public void ifPresent(Consumer<? super T> consumer) {
        if (isPresent()) {
            consumer.accept(this.value);
        }
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final FilterField<?> otherCasted = (FilterField<?>) other;
        return isPresent() == otherCasted.isPresent() && Objects.equals(getValue(), otherCasted.getValue());
    }

    @Override
    public String toString() {
        return "FilterField{value=" + value + "}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.value);
    }
}
