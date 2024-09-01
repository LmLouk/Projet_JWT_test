package WelcomeWise.jwt_servce.Authentification.Repository;

import WelcomeWise.jwt_servce.Authentification.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}
