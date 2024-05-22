package com.sp.standardtaskmanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.sp.standardtaskmanager.constant.Status;
import com.sp.standardtaskmanager.exception.ErrorDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

// @formatter:off
/**
 * Wrapper of final API Response. All endpoints are required to return API Response in this format
 * only.
 * <p>
 * There are 4 main sections of response.
 * <ul>
 *  <li>
 *   status = It will have value like SUCCESS, FAIL, and so on. This is very generic string representation of status
 *  </li>
 *  <li>
 *   code = It is actually a HttpStatus code for a request call.
 *  </li>
 *  <li>
 *   data = Response Payload. It can be anything based on API. Of course, this will be available when it is
 *   successful request.
 *   If call is not successful then "data" section will be completely absent from response. And we will show another
 *   section as error
 *  </li>
 *  <li>
 *   errors = Array of Error. Each error will contain "code" (service-error-code) and "message".
 *   We can add "target" property in error to specify the request param or part which causes the error (particularly
 *   in the case of validation errors)
 *  </li>
 * </ul>
 * <div>
 * <p>successful response sample</p>
 * <pre>{@code
 *
 *      {
 *        "status" : "SUCCESS",
 *        "code" : 200,
 *        "data" : { "id": 1234, "name": "demo name", "email": "demo@demo.com" }
 *      }
 *
 * }</pre>
 * </div>
 * <div>
 * <p>failure response sample 1</p>
 * <pre>{@code
 *
 *      {
 *        "status": "FAIL",
 *        "code" : 500,
 *        "errors": [
 *          { "code": 1000, "message": "demo message1" },
 *          { "code": 1001, "message": "demo message2" }
 *        ]
 *      }
 *
 * }</pre>
 * </div>
 * <div>
 * <p>failure response sample 2</p>
 * <pre>{@code
 *
 *      {
 *        "status": "FAIL",
 *        "code" : 500,
 *        "errors": [
 *          { "code": 1000, "message": "demo message1", "target": "taskIdentifier" },
 *          { "code": 1001, "message": "demo message2", "target": "taskTitle" }
 *        ]
 *      }
 *
 * }</pre>
 * </div>
 *
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
//@formatter:on
@JsonInclude(content = Include.NON_NULL)
@JsonPropertyOrder({"status", "data", "errors"})
@Getter
@Setter
@NoArgsConstructor
@Schema(description = "All details about the API Response.")
public class Response<T> {

    /**
     * status of Api Response.
     *
     * @see Status
     */
    @Schema(description = "API Status")
    private Status status;

    /**
     * Api Response Payload
     */
    @Schema(description = "Response Payload")
    private T data;
    /**
     * Error details when status is not {@link Status#SUCCESS}
     *
     * @see ErrorDetails
     */
    @Schema(description = "List of Errors")
    private List<ErrorDetails> errors;

    /**
     * @param status Status of Api Response.
     * @param data   Api Response Payload
     */
    public Response(@NonNull Status status, T data) {
        this.status = status;
        this.data = data;
    }

    /**
     * @param status Status of Api Response.
     * @param errors Error details when status is not {@link Status#SUCCESS}
     */
    public Response(@NonNull Status status, ErrorDetails... errors) {
        this.status = status;
        this.errors = List.of(errors);
    }
}
