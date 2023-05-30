package com.swm.studywithmentor.model.dto.query;

import com.swm.studywithmentor.model.entity.enrollment.EnrollmentStatus;
import com.swm.studywithmentor.model.entity.invoice.PaymentType;
import lombok.extern.slf4j.Slf4j;

import java.time.format.DateTimeFormatter;

@Slf4j
public enum FieldType {
    INTEGER {
        @Override
        public Object parse(String value) {
            return Integer.valueOf(value);
        }
    },
    DOUBLE {
        @Override
        public Object parse(String value) {
            return Double.valueOf(value);
        }
    },
    LONG {
        @Override
        public Object parse(String value) {
            return Long.valueOf(value);
        }
    },
    DATE {
        @Override
        public Object parse(String value) {
            Object date = null;
            try {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                date = dateTimeFormatter.parse(value);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            return date;
        }
    },
    STRING {
        @Override
        public Object parse(String value) {
            return value;
        }
    },
    CHAR {
        @Override
        public Object parse(String value) {
            return value.charAt(0);
        }
    },
    ENROLLMENT_STATUS{
        @Override
        public Object parse(String value) {
            return EnrollmentStatus.valueOf(value);
        }
    },
    PAYMENT_TYPE {
        @Override
        public Object parse(String value) {
            return PaymentType.valueOf(value);
        }
    },
    BOOLEAN {
        @Override
        public Object parse(String value) {
            return Boolean.valueOf(value);
        }
    };

    public abstract Object parse(String value);
}
