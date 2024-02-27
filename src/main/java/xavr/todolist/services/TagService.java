package xavr.todolist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xavr.todolist.domain.Tag;
import xavr.todolist.repositories.TagRepository;
import xavr.todolist.services.interfaces.ITagService;

import java.util.List;

@Service
public class TagService implements ITagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }


    @Override
    @Transactional
    public Tag findOrCreate(Tag tag) {
        List<Tag> foundTag = tagRepository.findByName(tag.getName());
        if (!foundTag.isEmpty()) {
            tagRepository.save(tag);
            return tag;
        } else {
            return foundTag.get(0);
        }


    }
}
