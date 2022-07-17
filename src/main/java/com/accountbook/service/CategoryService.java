package com.accountbook.service;

import java.util.List;
import java.util.stream.Collectors;

import com.accountbook.domain.entity.Category;
import com.accountbook.domain.repository.category.CategoryRepository;
import com.accountbook.dto.category.CategoryDto;
import com.accountbook.dto.category.CategoryRankDto;

import com.accountbook.dto.category.CategoryRequest;
import com.accountbook.exception.category.CategoryException;
import com.accountbook.exception.category.CategoryExceptionCode;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * 카테고리 목록 조회
     *
     * @return 카테고리 목록
     */
    @Transactional(readOnly = true)
    public List<CategoryDto> getCategoryList() throws Exception {

        var result = categoryRepository.findAll().stream()
                .map(CategoryDto::new)
                .filter(dto -> "Y".equals(dto.getUseYn()))
                .collect(Collectors.toList());

        return result;
    }

    @Transactional(readOnly = true)
    public List<CategoryRankDto> getCategoryRank(String userId) {
        var rank = CategoryRankDto.rank(categoryRepository.findByUserId(userId));
        return rank;
    }

    //카테고리 등록
    public Long enrollCategory(CategoryRequest categoryRequest) throws Exception{

        //Name, EventType이 동일한 카테고리가 존재하면, 이미 존재하는 카테고리로 판단하고 useYn만 Y로 업데이트
        List<Category> categories = categoryRepository.findByNameAndEventType(categoryRequest.getName(), categoryRequest.getEventType());
        Category category = null;

        if(categories != null && categories.size() == 1) {
            category = categories.get(0);

            Long defaultPrice = null;
            if(categoryRequest.getDefaultPrice() != null){
                defaultPrice = categoryRequest.getDefaultPrice();
            }
            category.changeUseYn("Y", defaultPrice);
            categoryRepository.save(category);
        } else {
           category = Category.createCategory(categoryRequest);
        }

        categoryRepository.save(category);
        return category.getSeq();
    }

    //카테고리 수정
    public CategoryDto updateCategory(CategoryRequest categoryRequest, long categorySeq) throws Exception{
        Category category = categoryRepository.findBySeq(categorySeq).orElseThrow(() -> new CategoryException(CategoryExceptionCode.NOT_FOUND));

        category.changeCategory(categoryRequest);
        categoryRepository.flush();

        return new CategoryDto(categoryRepository.findBySeq(categorySeq).get());
    }

    //카테고리 삭제
    public Boolean deleteCategory(long categorySeq) throws Exception{
        Category category = categoryRepository.findById(categorySeq).orElseThrow(() -> new CategoryException(CategoryExceptionCode.NOT_FOUND));

        //카테고리 삭제 시에는 EcoEvent 연관관계 때문에 삭제하지 않고 useYn 값만 N으로 변경
        //카테고리 전체 조회 시 useYn Y인 것만 노출
        category.changeUseYn("N", null);
        categoryRepository.save(category);

        return true;
    }
}