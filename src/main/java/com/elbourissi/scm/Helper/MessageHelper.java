package com.elbourissi.scm.Helper;

import lombok.*;

@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageHelper {
    private String content;
    @Builder.Default
    private MessageType type = MessageType.bleu;
}
