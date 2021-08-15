var api = {
    async getStudents() {
        const response = await fetch("http://localhost:8090/students");
        const student = await response.json();
        return student;
    },

    async createStudent(student, callback) {
        fetch("http://localhost:8090/students/", {
            method: "POST",
            body: JSON.stringify(student),
            headers: { "Content-type": "application/json; charset=UTF-8" },
        }).then(() => callback());
    },

    async updateStudent(student, callback) {
        fetch("http://localhost:8090/students/", {
            method: "PUT",
            body: JSON.stringify(student),
            headers: { "Content-type": "application/json; charset=UTF-8" },
        }).then(() => callback());
    },

    async deleteStudent(id, callback) {
        fetch("http://localhost:8090/students/" + id, {
            method: "DELETE",
        }).then(() => callback());
    },

    async getDepartments() {
        const response = await fetch("http://localhost:8090/departments");
        const department = await response.json();
        return department;
    },
}

export default api