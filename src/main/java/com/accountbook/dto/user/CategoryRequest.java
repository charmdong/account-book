package com.accountbook.dto.user;

import com.accountbook.domain.entity.Category;
import com.accountbook.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {

    private User user;
    private Category category;


}
