package com.example.finance.service;

import com.example.finance.exception.model.BackendException;
import com.example.finance.factory.BudgetMockFactory;
import com.example.finance.factory.CategoryMockFactory;
import com.example.finance.factory.TransferFundsMockFactory;
import com.example.finance.factory.UserMockFactory;
import com.example.finance.mapper.CategoryMapper;
import com.example.finance.mapper.UserAccountMapper;
import com.example.finance.model.dto.CategoryDto;
import com.example.finance.model.dto.TransferFunds;
import com.example.finance.model.dto.UserAccountDto;
import com.example.finance.model.entity.BudgetEntity;
import com.example.finance.model.entity.CategoryEntity;
import com.example.finance.model.entity.UserAccountEntity;
import com.example.finance.repository.BudgetRepository;
import com.example.finance.repository.CategoriesRepository;
import com.example.finance.repository.UserAccountRepository;
import com.example.finance.utils.MessageConstants;
import com.example.finance.utils.TestConstants;
import com.example.finance.utils.TransferFundsSetupHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    CategoriesRepository categoriesRepository;
    @Mock
    UserAccountService userAccountService;
    @Mock
    CategoryMapper categoryMapper;
    @Mock
    UserAccountMapper userAccountMapper;
    CategoryService categoryService;

    private CategoryDto categoryDto;
    private CategoryEntity categoryEntity;
    private UserAccountDto userAccountDto;
    private UserAccountEntity userAccountEntity;

    @BeforeEach
    public void setUp() {
        categoryService = new CategoryService(categoriesRepository, userAccountService, categoryMapper, userAccountMapper);
        categoryDto = CategoryMockFactory.createCategoryDto();
        categoryEntity = CategoryMockFactory.createCategoryEntity();
        userAccountDto = UserMockFactory.createUserDto();
        userAccountEntity = UserMockFactory.createUserEntity();
    }

    @Test
    void shouldReturnListOfCategoryDtos(){
        Mockito.when(categoriesRepository.findAll())
                .thenReturn(CategoryMockFactory.createCategoryEntities());
        Mockito.when(categoryMapper.toDtoList(CategoryMockFactory.createCategoryEntities()))
                .thenReturn(CategoryMockFactory.createCategoryDtos());

        List<CategoryDto> all = categoryService.getAll();

        Assertions.assertThat(all.getFirst()).isInstanceOf(CategoryDto.class);
    }

    @Test
    void shouldReturnCategoryDtoListByUserId() {
        Mockito.when(categoriesRepository.findByUserAccountEntityUserId(TestConstants.USER_UUID))
                .thenReturn(CategoryMockFactory.createCategoryEntities());
        Mockito.when(categoryMapper.toDtoList(CategoryMockFactory.createCategoryEntities()))
                .thenReturn(CategoryMockFactory.createCategoryDtos());

        List<CategoryDto> byUserId = categoryService.getByUserId(TestConstants.USER_UUID);

        Assertions.assertThat(byUserId.getFirst()).isInstanceOf(CategoryDto.class);
    }

    @Test
    void shouldReturnCategoryDtoById() {
        Mockito.when(categoriesRepository.findById(TestConstants.CATEGORY_UUID))
                .thenReturn(Optional.of(CategoryMockFactory.createCategoryEntity()));

        assertThatThrownBy(() -> categoryService.getById(TestConstants.CATEGORY_UUID))
                .isInstanceOf(BackendException.class)
                .hasMessageContaining(MessageConstants.CATEGORY_NOT_FOUND);
    }

//    @Test
//    void transferFunds_changeFundsBetweenCategories_success() {
//        // GIVEN
//        BigDecimal initialFromBudgetAmount = BigDecimal.valueOf(500.00);
//        BigDecimal initialToBudgetAmount = BigDecimal.valueOf(300.00);
//        TransferFundsSetupHelper helper = setupTransferFunds(initialFromBudgetAmount, initialToBudgetAmount);
//
//        // WHEN
//        categoryService.transferFundsBetweenCategories(helper.getTransferFunds());
//
//        // THEN
//        assertEquals(BigDecimal.valueOf(400.00), helper.getFromBudget().getAmount());
//        assertEquals(BigDecimal.valueOf(400.00), helper.getToBudget().getAmount());
//    }

    @Test
    void shouldCreateCategory() {
        // Given
        when(userAccountService.getById(categoryDto.userId())).thenReturn(userAccountDto);
        when(userAccountMapper.toEntity(userAccountDto)).thenReturn(userAccountEntity);
        when(categoryMapper.toEntity(categoryDto)).thenReturn(categoryEntity);
        when(categoriesRepository.save(categoryEntity)).thenReturn(categoryEntity);
        when(categoryMapper.toDto(categoryEntity)).thenReturn(categoryDto);

        // When
        CategoryDto result = categoryService.create(categoryDto);

        // Then
        Assertions.assertThat(result).isEqualTo(categoryDto);
        verify(categoriesRepository).save(categoryEntity);
    }

    @Test
    void shouldThrowBackendExceptionWhenUserNotFoundForCreate() {
        // Given
        when(userAccountService.getById(categoryDto.userId())).thenThrow(new BackendException(MessageConstants.USER_NOT_FOUND));

        // When & Then
        assertThatThrownBy(() -> categoryService.create(categoryDto))
                .isInstanceOf(BackendException.class)
                .hasMessageContaining(MessageConstants.USER_NOT_FOUND);
    }

    @Test
    void shouldUpdateCategory() {
        // Given
        when(categoriesRepository.findByIdWithLock(categoryDto.categoryId())).thenReturn(Optional.of(categoryEntity));
        when(categoryMapper.toEntity(categoryDto)).thenReturn(categoryEntity);
        when(categoriesRepository.save(categoryEntity)).thenReturn(categoryEntity);
        when(categoryMapper.toDto(categoryEntity)).thenReturn(categoryDto);

        // When
        CategoryDto result = categoryService.updateCategory(categoryDto);

        // Then
        Assertions.assertThat(result).isEqualTo(categoryDto);
        verify(categoriesRepository).save(categoryEntity);
    }

    @Test
    void shouldThrowBackendExceptionWhenCategoryNotFoundForUpdate() {
        // Given
        when(categoriesRepository.findByIdWithLock(categoryDto.categoryId())).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> categoryService.updateCategory(categoryDto))
                .isInstanceOf(BackendException.class)
                .hasMessageContaining(MessageConstants.CATEGORY_NOT_FOUND);
    }

    @Test
    void shouldReturnCategoryByUserIdAndName() {
        // Given
        when(categoriesRepository.findByUserAccountEntityUserIdAndName(TestConstants.USER_UUID, "Test Category"))
                .thenReturn(Optional.of(categoryEntity));

        // When
        CategoryEntity result = categoryService.getByUserIdAndCategoryName(TestConstants.USER_UUID, "Test Category");

        // Then
        Assertions.assertThat(result).isEqualTo(categoryEntity);
    }

    @Test
    void shouldThrowBackendExceptionWhenCategoryNotFoundByUserIdAndName() {
        // Given
        when(categoriesRepository.findByUserAccountEntityUserIdAndName(TestConstants.USER_UUID, "Test Category"))
                .thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> categoryService.getByUserIdAndCategoryName(TestConstants.USER_UUID, "Test Category"))
                .isInstanceOf(BackendException.class)
                .hasMessageContaining(MessageConstants.SOURCE_CATEGORY);
    }

//    @Test
//    void transferFunds_changeFundsBetweenCategories_insufficientFunds() {
//        //GIVEN
//        BigDecimal initialFromBudgetAmount = BigDecimal.valueOf(50.00);
//        BigDecimal initialToBudgetAmount = BigDecimal.valueOf(300.00);
//        TransferFundsSetupHelper helper = setupTransferFunds(initialFromBudgetAmount, initialToBudgetAmount);
//
//        //WHEN, THEN
//        BackendException exception = assertThrows(BackendException.class, () ->
//                categoryService
//                        .transferFundsBetweenCategories(helper.getTransferFunds()));
//        assertEquals(MessageConstants.INSUFFICIENT_FUNDS, exception.getMessage());
//    }
//
//    private TransferFundsSetupHelper setupTransferFunds(BigDecimal fromBudgetAmount, BigDecimal toBudgetAmount) {
//        UserAccountEntity user = UserMockFactory.createUserEntity();
//        CategoryEntity fromCategory = CategoryMockFactory.createCategoryEntityWithUser(user);
//        CategoryEntity toCategory = CategoryMockFactory.createCategoryEntityWithUser(user);
//        BudgetEntity fromBudget = BudgetMockFactory.createBudgetEntity(user, fromCategory);
//        BudgetEntity toBudget = BudgetMockFactory.createBudgetEntity(user, toCategory);
//        TransferFunds transferFunds = TransferFundsMockFactory.createTransferFundsDto();
//
//        fromBudget.setAmount(fromBudgetAmount);
//        toBudget.setAmount(toBudgetAmount);
//
//        when(categoriesRepository
//                .findByUserAccountEntityUserIdAndName(transferFunds.userId(), transferFunds.fromCategoryName()))
//                .thenReturn(Optional.of(fromCategory));
//        when(categoriesRepository
//                .findByUserAccountEntityUserIdAndName(transferFunds.userId(), transferFunds.toCategoryName()))
//                .thenReturn(Optional.of(toCategory));
//        when(budgetService.getByUserIdAndCategoryIdAndBudgetId(transferFunds.userId(),
//                fromCategory.getCategoryId(), transferFunds.fromBudgetId()))
//                .thenReturn(fromBudget);
//        when(budgetService.getByUserIdAndCategoryIdAndBudgetId(transferFunds.userId(),
//                toCategory.getCategoryId(), transferFunds.toBudgetId()))
//                .thenReturn(toBudget);
//
//        return new TransferFundsSetupHelper(user, fromCategory, toCategory, fromBudget, toBudget, transferFunds);
//    }
}