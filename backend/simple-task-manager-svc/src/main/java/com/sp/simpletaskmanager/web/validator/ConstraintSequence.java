package com.sp.simpletaskmanager.web.validator;

import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

/**
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@GroupSequence({Default.class, Extended.class})
public interface ConstraintSequence {

}
