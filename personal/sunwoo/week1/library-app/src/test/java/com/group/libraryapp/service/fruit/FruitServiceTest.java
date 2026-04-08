package com.group.libraryapp.service.fruit;

import com.group.libraryapp.domain.user.Fruit;
import com.group.libraryapp.domain.user.FruitRepository;
import com.group.libraryapp.dto.fruit.response.FruitListResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FruitServiceTest {

    @Mock
    private FruitRepository fruitRepository;

    @InjectMocks
    private FruitService fruitService;

    @Test
    void getFruitListWithGteOption() {
        // given
        Fruit apple = new Fruit("apple", LocalDate.of(2026, 3, 24), 3000L);
        when(fruitRepository.findAllNotSoldByPriceGte(3000L))
                .thenReturn(List.of(apple));

        // when
        List<FruitListResponse> result = fruitService.getFruitList("GTE", 3000L);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("apple");
        assertThat(result.get(0).getPrice()).isEqualTo(3000L);
        assertThat(result.get(0).getWarehousingDate()).isEqualTo(LocalDate.of(2026, 3, 24));
        verify(fruitRepository).findAllNotSoldByPriceGte(3000L);
    }

    @Test
    void getFruitListWithLteOption() {
        // given
        Fruit banana = new Fruit("banana", LocalDate.of(2026, 3, 24), 5000L);
        when(fruitRepository.findAllNotSoldByPriceLte(5000L))
                .thenReturn(List.of(banana));

        // when
        List<FruitListResponse> result = fruitService.getFruitList("LTE", 5000L);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("banana");
        assertThat(result.get(0).getPrice()).isEqualTo(5000L);
        assertThat(result.get(0).getWarehousingDate()).isEqualTo(LocalDate.of(2026, 3, 24));
        verify(fruitRepository).findAllNotSoldByPriceLte(5000L);
    }

    @Test
    void throwExceptionWhenOptionIsInvalid() {
        assertThatThrownBy(() -> fruitService.getFruitList("GT", 3000L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("option must be GTE or LTE");
    }
}


