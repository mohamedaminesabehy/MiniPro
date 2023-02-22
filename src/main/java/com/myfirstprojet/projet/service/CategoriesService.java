package com.myfirstprojet.projet.service;
import com.myfirstprojet.projet.repository.CategoriesRepository;
import com.myfirstprojet.projet.entity.Categories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service

public class CategoriesService {
    @Autowired
    private final CategoriesRepository categoriesRepository;

    @Autowired
    public CategoriesService(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    public List<Categories> getCategories(){

        return categoriesRepository.findAll();
    }

    public Optional<Categories> getcategoriesbyid(Long id)
    {

        return categoriesRepository.findById(id);
    }

    public Categories save(Categories categories)
    {

        return categoriesRepository.saveAndFlush(categories);
    }
    public boolean existsById(Long id){
        return categoriesRepository.existsById(id);
}

    public void deleteCategories (Long id)
    {
        categoriesRepository.deleteById(id);

    }
    public List<Categories> search(String query) {
        return categoriesRepository.findByNomContainingIgnoreCase(query);
    }

}
