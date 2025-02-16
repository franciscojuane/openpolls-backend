package com.francisco.openpolls.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.francisco.openpolls.model.Poll;
import com.francisco.openpolls.repository.PollRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class PollService {

	@Autowired
	PollRepository pollRepository;
	
	public Page<Poll> findAll(Pageable pageable){
		return pollRepository.findAll(pageable);
	}
	
	public Poll findById(Long id) {
        return pollRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Poll not found with id: " + id));
    }
	
	@Transactional
	public Poll save(Poll poll) {
        return pollRepository.save(poll);
    }
	
    @Transactional
    public Poll update(Poll updatedPoll) {
        Poll existingPoll = pollRepository.findById(updatedPoll.getId())
                .orElseThrow(() -> new EntityNotFoundException("Poll not found for updating with id: " + updatedPoll.getId()));

        existingPoll.setName(updatedPoll.getName());
        existingPoll.setDescription(updatedPoll.getDescription());
        existingPoll.setEffectiveDate(updatedPoll.getEffectiveDate());
        existingPoll.setExpirationDate(updatedPoll.getExpirationDate());

        return pollRepository.save(existingPoll);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!pollRepository.existsById(id)) {
            throw new EntityNotFoundException("Poll not found for id " + id);
        }
        pollRepository.deleteById(id);
    }
	
}
