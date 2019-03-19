package seedu.address.model.equipment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalEquipments.ACHORVALECC;
import static seedu.address.testutil.TypicalEquipments.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.equipment.exceptions.DuplicateEquipmentException;
import seedu.address.model.equipment.exceptions.EquipmentNotFoundException;
import seedu.address.testutil.EquipmentBuilder;

public class UniqueEquipmentListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueEquipmentList uniqueEquipmentList = new UniqueEquipmentList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEquipmentList.contains(null);
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueEquipmentList.contains(ACHORVALECC));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueEquipmentList.add(ACHORVALECC);
        assertTrue(uniqueEquipmentList.contains(ACHORVALECC));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueEquipmentList.add(ACHORVALECC);
        Equipment editedAlice = new EquipmentBuilder(ACHORVALECC).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueEquipmentList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEquipmentList.add(null);
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueEquipmentList.add(ACHORVALECC);
        thrown.expect(DuplicateEquipmentException.class);
        uniqueEquipmentList.add(ACHORVALECC);
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEquipmentList.setEquipment(null, ACHORVALECC);
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEquipmentList.setEquipment(ACHORVALECC, null);
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        thrown.expect(EquipmentNotFoundException.class);
        uniqueEquipmentList.setEquipment(ACHORVALECC, ACHORVALECC);
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueEquipmentList.add(ACHORVALECC);
        uniqueEquipmentList.setEquipment(ACHORVALECC, ACHORVALECC);
        UniqueEquipmentList expectedUniqueEquipmentList = new UniqueEquipmentList();
        expectedUniqueEquipmentList.add(ACHORVALECC);
        assertEquals(expectedUniqueEquipmentList, uniqueEquipmentList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueEquipmentList.add(ACHORVALECC);
        Equipment editedAlice = new EquipmentBuilder(ACHORVALECC).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueEquipmentList.setEquipment(ACHORVALECC, editedAlice);
        UniqueEquipmentList expectedUniqueEquipmentList = new UniqueEquipmentList();
        expectedUniqueEquipmentList.add(editedAlice);
        assertEquals(expectedUniqueEquipmentList, uniqueEquipmentList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueEquipmentList.add(ACHORVALECC);
        uniqueEquipmentList.setEquipment(ACHORVALECC, BOB);
        UniqueEquipmentList expectedUniqueEquipmentList = new UniqueEquipmentList();
        expectedUniqueEquipmentList.add(BOB);
        assertEquals(expectedUniqueEquipmentList, uniqueEquipmentList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueEquipmentList.add(ACHORVALECC);
        uniqueEquipmentList.add(BOB);
        thrown.expect(DuplicateEquipmentException.class);
        uniqueEquipmentList.setEquipment(ACHORVALECC, BOB);
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEquipmentList.remove(null);
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        thrown.expect(EquipmentNotFoundException.class);
        uniqueEquipmentList.remove(ACHORVALECC);
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueEquipmentList.add(ACHORVALECC);
        uniqueEquipmentList.remove(ACHORVALECC);
        UniqueEquipmentList expectedUniqueEquipmentList = new UniqueEquipmentList();
        assertEquals(expectedUniqueEquipmentList, uniqueEquipmentList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEquipmentList.setEquipments((UniqueEquipmentList) null);
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueEquipmentList.add(ACHORVALECC);
        UniqueEquipmentList expectedUniqueEquipmentList = new UniqueEquipmentList();
        expectedUniqueEquipmentList.add(BOB);
        uniqueEquipmentList.setEquipments(expectedUniqueEquipmentList);
        assertEquals(expectedUniqueEquipmentList, uniqueEquipmentList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueEquipmentList.setEquipments((List<Equipment>) null);
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueEquipmentList.add(ACHORVALECC);
        List<Equipment> equipmentList = Collections.singletonList(BOB);
        uniqueEquipmentList.setEquipments(equipmentList);
        UniqueEquipmentList expectedUniqueEquipmentList = new UniqueEquipmentList();
        expectedUniqueEquipmentList.add(BOB);
        assertEquals(expectedUniqueEquipmentList, uniqueEquipmentList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Equipment> listWithDuplicateEquipments = Arrays.asList(ACHORVALECC, ACHORVALECC);
        thrown.expect(DuplicateEquipmentException.class);
        uniqueEquipmentList.setEquipments(listWithDuplicateEquipments);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueEquipmentList.asUnmodifiableObservableList().remove(0);
    }
}
