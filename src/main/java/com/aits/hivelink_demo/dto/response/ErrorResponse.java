    package com.aits.hivelink_demo.dto.response;

    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    import org.springframework.http.HttpStatus;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class ErrorResponse {

        private String requestId;
        private String timestamp;

        private String message;
        private String path;

        private Status status;

        private Object data;

        public static class Status {
            private int code;
            private String type;

            public Status(HttpStatus status) {
                this.code = status.value();
                this.type = status.name();
            }

            public int getCode() {
                return code;
            }

            public String getType() {
                return type;
            }
        }

    }
