package com.repository;

import com.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface VadUserRepository extends JpaRepository<VadUser,Long> {
	VadUser findByEmail(String email);
}
