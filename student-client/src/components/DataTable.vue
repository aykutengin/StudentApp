<template>
  <v-data-table
    :headers="headers"
    :items="students"
    sort-by="calories"
    class="elevation-1"
  >
    <template v-slot:top>
      <v-toolbar flat>
        <v-toolbar-title>Student CRUD</v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="500px">
          <template v-slot:activator="{ on, attrs }">
            <v-btn color="primary" dark class="mb-2" v-bind="attrs" v-on="on">
              New Student
            </v-btn>
          </template>
          <v-card>
            <v-card-title>
              <span class="text-h5">{{ formTitle }}</span>
            </v-card-title>

            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field
                      v-model="editedStudent.name"
                      label="Name"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field
                      v-model="editedStudent.lastName"
                      label="Last Name"
                    ></v-text-field>
                  </v-col>
                  <v-col>
                    <v-select :items="departments" item-text="name" v-model="editedStudent.department" label="Department"></v-select>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="close"> Cancel </v-btn>
              <v-btn color="blue darken-1" text @click="save"> Save </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-dialog v-model="dialogDelete" max-width="500px">
          <v-card>
            <v-card-title class="text-h5"
              >Are you sure you want to delete this item?</v-card-title
            >
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="closeDelete"
                >Cancel</v-btn
              >
              <v-btn color="blue darken-1" text @click="deleteItemConfirm"
                >OK</v-btn
              >
              <v-spacer></v-spacer>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-toolbar>
    </template>
    <template v-slot:item.actions="{ item }">
      <v-icon small class="mr-2" @click="editItem(item)"> mdi-pencil </v-icon>
      <v-icon small @click="deleteItem(item)"> mdi-delete </v-icon>
    </template>
    <template v-slot:no-data>
      <v-btn color="primary" @click="initialize"> Reset </v-btn>
    </template>
  </v-data-table>
</template>

<script>
import api from "@/service/api";

export default {
  data: () => ({
    dialog: false,
    dialogDelete: false,
    headers: [
      {
        text: "ID",
        align: "start",
        sortable: false,
        value: "id",
      },
      { text: "Name", value: "name" },
      { text: "Last Name", value: "lastName" },
      { text: "Department", value: "department.name" },
      { text: "Actions", value: "actions", sortable: false },
    ],
    students: [],
    departments: [],
    editedIndex: -1,
    editedStudent: {
      id: 0,
      name: "",
      lastName: "",
      department: {},
    },
    defaultItem: {
      name: "",
      lastName: "",
      department: "",
    },
  }),
  computed: {
    formTitle() {
      return this.editedIndex === -1 ? "New Student" : "Edit Student";
    },
  },
  watch: {
    dialog(val) {
      val || this.close();
    },
    dialogDelete(val) {
      val || this.closeDelete();
    },
  },
  created() {
    this.initialize();
  },
  methods: {
    initialize() {
      api.getStudents().then((data) => (this.students = data));
      api.getDepartments().then((data) => (this.departments = data));
    },
    editItem(item) {
      this.editedIndex = this.students.indexOf(item);
      this.editedStudent = Object.assign({}, item);
      this.dialog = true;
    },
    deleteItem(item) {
      this.editedIndex = item.id;
      this.editedStudent = Object.assign({}, item);
      this.dialogDelete = true;
    },
    deleteItemConfirm() {
      this.students.splice(this.editedIndex, 1);

      this.closeDelete();

      api.deleteStudent(this.editedIndex, () => {
        return this.initialize();
      });
    },
    close() {
      this.dialog = false;
      this.$nextTick(() => {
        this.editedStudent = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },
    closeDelete() {
      this.dialogDelete = false;
      this.$nextTick(() => {
        this.editedStudent = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },
    save() {
      if (this.editedIndex > -1) {
        api.updateStudent(this.editedStudent, () => {
          return this.initialize();
        });
      } else {
        api.createStudent(this.editedStudent, () => {
          return this.initialize();
        });
      }
      this.close();
    },
  },
};
</script>