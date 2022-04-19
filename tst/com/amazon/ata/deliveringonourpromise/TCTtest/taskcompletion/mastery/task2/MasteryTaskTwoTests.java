package com.amazon.ata.deliveringonourpromise.TCTtest.taskcompletion.mastery.task2;

import com.amazon.ata.deliveringonourpromise.data.OrderDatastore;
import com.amazon.ata.deliveringonourpromise.ordermanipulationauthority.OrderManipulationAuthorityClient;
import com.amazon.ata.order.OrderFieldValidator;
import com.amazon.ata.ordermanipulationauthority.OrderManipulationAuthority;
import com.amazon.ata.ordermanipulationauthority.OrderResult;
import com.amazon.ata.test.helper.AtaTestHelper;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class MasteryTaskTwoTests {

    @Test
    public void masteryTaskTwo_masteryTaskTwoFile_existsAndIncludesMultipleTests() {
        // GIVEN
        String masteryTaskTwoFileName = "OrderDaoTestPlan.md";

        // WHEN
        String content = AtaTestHelper.getFileContentFromResources(masteryTaskTwoFileName);
        Pattern methodNames = Pattern.compile("get_[^_]+_.+");
        Matcher methodNameMatcher = methodNames.matcher(content);
        List<String> matches = new ArrayList<>();
        while (methodNameMatcher.find()) {
            matches.add(methodNameMatcher.group());
        }

        // THEN
        assertThat(matches.size())
            .as("Expected multiple test names in OrderDaoTestPlan.md!")
            .isGreaterThan(1);
    }
    @Test
    public void get_OrderNumberInvalid_returnsNull(){



    }



}
