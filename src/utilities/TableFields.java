package utilities;

public class TableFields {

    public static class PersonFields {
        private PersonFields() {}
        public static final String ID = "id";
        public static final String LAST_NAME = "last_name";
        public static final String FIRST_NAMES = "first_names";
        public static final String FUNCTION = "function";
        public static final String SERVICE_ID = "service_id";
    }

    public static class MaterialFields {
        private MaterialFields() {}
        public static final String ID = "id";
        public static final String DESIGNATION = "designation";
        public static final String SPECIFICATION = "specification";
        public static final String BRAND = "brand";
        public static final String SERIAL_NUMBER = "serial_number";
        public static final String ACQUISITION_DATE = "acquisition_date";
        public static final String STATE = "state";
        public static final String IS_AVAILABLE = "is_available";
        public static final String TYPE_ID = "type_id";
        public static final String PACK_ID = "pack_id";
    }

    public static class ServiceFields {
        private ServiceFields() {}
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
    }

    public static class BreakdownFields {
        private BreakdownFields() {}
        public static final String ID = "id";
        public static final String DESCRIPTION = "description";
    }

    public static class TechnicianFields {
        private TechnicianFields() {}
        public static final String ID = "id";
        public static final String LAST_NAME = "last_name";
        public static final String FIRST_NAMES = "first_name";
    }

    public static class MaterialTypeFields {
        private MaterialTypeFields() {}
        public static final String ID = "id";
        public static final String NAME = "name";
    }

    public static class ComputerPackFields {
        private ComputerPackFields() {}
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
    }

    public static class InterventionFields {
        private InterventionFields() {}
        public static final String INTERVENTION_DATE = "intervention_date";
        public static final String MATERIAL_ID = "material_id";
        public static final String BREAKDOWN_ID = "breakdown_id";
        public static final String TECHNICIAN_ID = "technician_id";
    }

    public static class AssignmentFields {
        private AssignmentFields() {}
        public static final String START_DATE = "start_date";
        public static final String END_DATE = "end_date";
        public static final String MATERIAL_ID = "material_id";
        public static final String PERSON_ID = "person_id";
    }

    public static class AdministratorFields {
        private AdministratorFields() {}
        public static final String ID = "id";
        public static final String LOGIN = "login";
        public static final String PASSWORD = "password";
    }

    public static class BreakdownOccurrenceFields {
        private BreakdownOccurrenceFields() {}
        public static final String BREAKDOWN_DATE = "breakdown_date";
        public static final String MATERIAL_ID = "material_id";
        public static final String BREAKDOWN_ID = "breakdown_id";
    }

    public static class DatabaseStatusFields {
        private DatabaseStatusFields() {}
        public static final String ID = "id";
        public static final String DB_INITIALIZED = "db_initialized";
        public static final String DB_ADMINISTRATED = "db_administrated";
    }

}