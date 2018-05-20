package com.nchu.common.Form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by user12 on 2018/2/26.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionForm {
    @NotBlank(message = "内容不能为空")
    private String content;
    @NotBlank(message = "标题不能为空")
    private String title;
    @NotNull(message = "课程类型不能为空")
    private Long courseId;

}
