package com.gbtec.email.business.application.model;

import com.gbtec.utils.filters.FilterField;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.List;

@Builder
@Data
@ToString
@EqualsAndHashCode
public class EmailEntityFilter {
    @NonNull
    @Builder.Default
    private FilterField<List<Long>> uuids = FilterField.empty();

    @NonNull
    @Builder.Default
    private FilterField<List<String>> froms = FilterField.empty();

    @NonNull
    @Builder.Default
    private FilterField<List<String>> tos = FilterField.empty();

    @NonNull
    @Builder.Default
    private FilterField<List<String>> ccs = FilterField.empty();

    @NonNull
    @Builder.Default
    private FilterField<List<EmailState>> states = FilterField.empty();

    @NonNull
    @Builder.Default
    private FilterField<String> body = FilterField.empty();
}
