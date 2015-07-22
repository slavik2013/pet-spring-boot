package ua.in.petybay.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import ua.in.petybay.entity.Option;

/**
 * Created by slavik on 20.07.15.
 */
public interface OptionRepository extends PagingAndSortingRepository<Option, String> {
}
