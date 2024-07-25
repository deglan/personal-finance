package com.example.finance.service;

import com.example.finance.exception.model.BackendException;
import com.example.finance.factory.BudgetMockFactory;
import com.example.finance.factory.CategoryMockFactory;
import com.example.finance.factory.UserMockFactory;
import com.example.finance.mapper.BudgetMapper;
import com.example.finance.mapper.CategoryMapper;
import com.example.finance.mapper.UserAccountMapper;
import com.example.finance.model.dto.BudgetDto;
import com.example.finance.model.dto.CategoryDto;
import com.example.finance.model.dto.UserAccountDto;
import com.example.finance.model.entity.BudgetEntity;
import com.example.finance.model.entity.CategoryEntity;
import com.example.finance.model.entity.UserAccountEntity;
import com.example.finance.repository.BudgetRepository;
import com.example.finance.utils.MessageConstants;
import com.example.finance.utils.TestConstants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BudgetServiceTest {

    @Mock
    BudgetRepository budgetRepository;
    @Mock
    CategoryService categoryService;
    @Mock
    CategoryMapper categoryMapper;
    @Mock
    UserAccountService userAccountService;
    @Mock
    UserAccountMapper userAccountMapper;
    @Mock
    BudgetMapper budgetMapper;
    @Mock
    ApplicationEventPublisher applicationEventPublisher;
    BudgetService budgetService;

    @BeforeEach
    public void setUp() {
        budgetService = new BudgetService(budgetRepository, categoryService, categoryMapper, userAccountService, userAccountMapper, budgetMapper, applicationEventPublisher);
    }

    @Test
    public void shouldReturnListOfBudgetDtoByUserAccountId() {
        // GIVEN
        List<BudgetEntity> budgetEntities = BudgetMockFactory.createBudgetEntities();
        List<BudgetDto> budgetDtos = BudgetMockFactory.createBudgetDtos();
        when(budgetRepository.findByUserAccountEntityUserId(TestConstants.USER_UUID))
                .thenReturn(budgetEntities);
        when(budgetMapper.toDtoList(budgetEntities))
                .thenReturn(budgetDtos);
        // WHEN
        List<BudgetDto> serviceByUserId = budgetService.getByUserId(TestConstants.USER_UUID);

        // THEN
        assertThat(serviceByUserId).isNotNull();
        assertThat(serviceByUserId.get(0)).isNotNull();
    }

    @Test
    void shouldReturnBudgetDtoByBudgetId() {
        //GIVEN
        when(budgetRepository.findById(TestConstants.BUDGET_UUID))
                .thenReturn(Optional.ofNullable(BudgetMockFactory.createBudgetEntity()));
        when(budgetMapper.toDto(BudgetMockFactory.createBudgetEntity()))
                .thenReturn(BudgetMockFactory.createBudgetDto());

        //WHEN
        BudgetDto byId = budgetService.getById(TestConstants.BUDGET_UUID);

        //THEN
        Assertions.assertThat(byId).isInstanceOf(BudgetDto.class);
    }

    @Test
    void shouldThrowBackendExceptionBudgetNotFFound() {
        // GIVEN
        when(budgetRepository.findById(TestConstants.BUDGET_UUID))
                .thenReturn(Optional.empty());

        // WHEN & THEN
        Assertions.assertThatThrownBy(() -> budgetService.getById(TestConstants.BUDGET_UUID))
                .isInstanceOf(BackendException.class)
                .hasMessageContaining("No budget found for id: " + TestConstants.BUDGET_UUID);
    }

    @Test
    void shouldReturnBudgetEntityByUserIdCategoryIdAndBudgetId() {
        // GIVEN
        BudgetEntity budgetEntity = BudgetMockFactory.createBudgetEntity();
        when(budgetRepository.findByUserAccountEntityUserIdAndCategoryEntityCategoryIdAndBudgetId(
                TestConstants.USER_UUID, TestConstants.CATEGORY_UUID, TestConstants.BUDGET_UUID))
                .thenReturn(Optional.of(budgetEntity));

        // WHEN
        BudgetEntity result = budgetService.getByUserIdAndCategoryIdAndBudgetId(
                TestConstants.USER_UUID, TestConstants.CATEGORY_UUID, TestConstants.BUDGET_UUID);

        // THEN
        Assertions.assertThat(result).isEqualTo(budgetEntity);
    }

    @Test
    void shouldThrowBackendExceptionWhenBudgetNotFound() {
        // GIVEN
        when(budgetRepository.findByUserAccountEntityUserIdAndCategoryEntityCategoryIdAndBudgetId(
                TestConstants.USER_UUID, TestConstants.CATEGORY_UUID, TestConstants.BUDGET_UUID))
                .thenReturn(Optional.empty());

        // WHEN & THEN
        Assertions.assertThatThrownBy(() -> budgetService.getByUserIdAndCategoryIdAndBudgetId(
                        TestConstants.USER_UUID, TestConstants.CATEGORY_UUID, TestConstants.BUDGET_UUID))
                .isInstanceOf(BackendException.class)
                .hasMessageContaining(MessageConstants.SOURCE_BUDGET);
    }

    @Test
    void shouldCreateBudget() {
        // GIVEN
        BudgetDto budgetDto = BudgetMockFactory.createBudgetDto();
        BudgetEntity budgetEntity = BudgetMockFactory.createBudgetEntity();
        UserAccountEntity userAccountEntity = UserMockFactory.createUserEntity();
        UserAccountDto userDto = UserMockFactory.createUserDto();
        CategoryEntity categoryEntity = CategoryMockFactory.createCategoryEntity();
        CategoryDto categoryDto = CategoryMockFactory.createCategoryDto();

        when(userAccountService.getById(budgetDto.userId()))
                .thenReturn(userDto);
        when(categoryService.getById(budgetDto.categoryId()))
                .thenReturn(categoryDto);
        when(categoryMapper.toEntity(categoryDto)).thenReturn(categoryEntity);
        when(budgetMapper.toEntity(budgetDto)).thenReturn(budgetEntity);
        when(budgetRepository.save(budgetEntity)).thenReturn(budgetEntity);
        when(budgetMapper.toDto(budgetEntity)).thenReturn(budgetDto);

        // WHEN
        BudgetDto result = budgetService.create(budgetDto);

        // THEN
        Assertions.assertThat(result).isEqualTo(budgetDto);
    }

    @Test
    void shouldUpdateBudget() {
        // GIVEN
        BudgetDto budgetDto = BudgetMockFactory.createBudgetDto();
        BudgetEntity budgetEntity = BudgetMockFactory.createBudgetEntity();

        when(budgetMapper.toEntity(budgetDto)).thenReturn(budgetEntity);
        when(budgetRepository.saveAndFlush(budgetEntity)).thenReturn(budgetEntity);
        when(budgetMapper.toDto(budgetEntity)).thenReturn(budgetDto);

        // WHEN
        BudgetDto result = budgetService.updateBudget(budgetDto);

        // THEN
        Assertions.assertThat(result).isEqualTo(budgetDto);
    }

    @Test
    void shouldThrowBackendExceptionWhenBudgetToUpdateNotFound() {
        // GIVEN
        BudgetDto budgetDto = BudgetMockFactory.createBudgetDto();
        BudgetEntity budgetEntity = BudgetMockFactory.createBudgetEntity();

        when(budgetMapper.toEntity(budgetDto)).thenReturn(budgetEntity);
        when(budgetRepository.saveAndFlush(budgetEntity)).thenThrow(new BackendException("Budget not found"));

        // WHEN & THEN
        Assertions.assertThatThrownBy(() -> budgetService.updateBudget(budgetDto))
                .isInstanceOf(BackendException.class)
                .hasMessageContaining("Budget not found");
    }

    @Test
    void shouldDeleteBudget() {
        // GIVEN
        BudgetEntity budgetEntity = BudgetMockFactory.createBudgetEntity();
        when(budgetRepository.findById(TestConstants.BUDGET_UUID))
                .thenReturn(Optional.of(budgetEntity));

        // WHEN
        budgetService.deleteBudget(TestConstants.BUDGET_UUID);

        // THEN
        verify(budgetRepository).deleteById(TestConstants.BUDGET_UUID);
    }

    @Test
    void shouldThrowBackendExceptionWhenBudgetToDeleteNotFound() {
        // GIVEN
        when(budgetRepository.findById(TestConstants.BUDGET_UUID))
                .thenReturn(Optional.empty());

        // WHEN & THEN
        Assertions.assertThatThrownBy(() -> budgetService.deleteBudget(TestConstants.BUDGET_UUID))
                .isInstanceOf(BackendException.class)
                .hasMessageContaining(MessageConstants.BUDGET_NOT_FOUND_EXCEPTION_MESSAGE + TestConstants.BUDGET_UUID);
    }
}
