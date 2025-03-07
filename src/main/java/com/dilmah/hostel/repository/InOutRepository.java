package com.dilmah.hostel.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import com.dilmah.hostel.model.Hosteller;
import com.dilmah.hostel.model.InOut;

@RepositoryRestResource(path = "inout")
@PreAuthorize("hasAnyRole('WARDEN', 'HOSTELLER', 'SUPERADMIN')")
public interface InOutRepository extends JpaRepository<InOut, Long> {
	Optional<InOut> findByOtp1(String otpIn);
    Optional<InOut> findByOtp2(String otpOut);
    List<InOut> findByHosteller(Optional<Hosteller> hosteller);
}
