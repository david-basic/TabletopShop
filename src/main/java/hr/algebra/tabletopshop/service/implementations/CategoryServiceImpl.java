package hr.algebra.tabletopshop.service.implementations;

import hr.algebra.tabletopshop.dto.CreateCategoryFormDto;
import hr.algebra.tabletopshop.dto.UpdateCategoryFormDto;
import hr.algebra.tabletopshop.exceptions.DbEntityNotFoundException;
import hr.algebra.tabletopshop.model.items.Category;
import hr.algebra.tabletopshop.repository.CategoryRepositoryMongo;
import hr.algebra.tabletopshop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepositoryMongo categoryRepositoryMongo;
    private final MongoTemplate mongoTemplate;
    
    @Override
    public void createCategory(CreateCategoryFormDto createCategoryFormDto) {
        categoryRepositoryMongo.save(Category.builder()
                                             .categoryId(calculateNextCategoryIdInSequence())
                                             .name(createCategoryFormDto.getName())
                                             .build());
    }
    
    @Override
    public Category getCategoryById(String id) {
        return categoryRepositoryMongo.findById(id).orElseThrow(DbEntityNotFoundException::new);
    }
    
    @Override
    public List<Category> getAllCategories() {
        return categoryRepositoryMongo.findAll();
    }
    
    @Override
    public void deleteCategory(String id) {
        categoryRepositoryMongo.deleteById(id);
    }
    
    @Override
    public void updateCategory(UpdateCategoryFormDto updateCategoryFormDto) {
        Category categoryToUpdate = categoryRepositoryMongo.findById(updateCategoryFormDto.getId()).orElseThrow(DbEntityNotFoundException::new);
        categoryToUpdate.setName(updateCategoryFormDto.getName());
        categoryRepositoryMongo.save(categoryToUpdate);
    }
    
    private Integer calculateNextCategoryIdInSequence() {
        Query query = new Query().with(Sort.by(Sort.Order.desc("categoryId"))).limit(1);
        Category lastCategory = mongoTemplate.findOne(query, Category.class);
        if (lastCategory != null) {
            return lastCategory.getCategoryId() + 1;
        }
        return 1;
    }
}
