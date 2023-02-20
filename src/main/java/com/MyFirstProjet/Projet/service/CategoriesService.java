package com.MyFirstProjet.Projet.service;
import com.MyFirstProjet.Projet.repository.CategoriesRepository;
import com.MyFirstProjet.Projet.entity.Categories;
import lombok.Builder;
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

    public Optional<Categories> getcategoriesbyid(Long Id)
    {

        return categoriesRepository.findById(Id);
    }

    public Categories save(Categories categories)
    {

        return categoriesRepository.saveAndFlush(categories);
    }
    public boolean existsById(Long Id){
        return categoriesRepository.existsById(Id);
}

    public void deleteCategories (Long Id)
    {
        categoriesRepository.deleteById(Id);

    }
    public List<Categories> search(String query) {
        return categoriesRepository.findByNomContainingIgnoreCase(query);
    }

}
