package com.example.agentapp.repository.user;

import com.example.agentapp.model.user.User;
import com.example.agentapp.model.user.UserPrivilege;
import com.example.agentapp.model.enums.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPrivilegeRepository extends JpaRepository<UserPrivilege, Long> {
    List<UserPrivilege> findAllByUserId(long parseLong);
    UserPrivilege findOneByUserIdAndPrivilege(long parseLong, Privilege toEnum);
    List<UserPrivilege> findByUser(User u);
    UserPrivilege findByUserAndPrivilege(User u, Privilege addDiscount);
}
