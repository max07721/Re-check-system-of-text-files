<template>
	<div>
			<el-form-item v-if="$check_register_field('add','student_id','/student_users/view')" label="student number" prop="student_id">
				<el-input id="student_id" v-model="form['student_id']" placeholder="Please enter your student number"></el-input>
				</el-form-item>
				<el-form-item v-if="$check_register_field('add','student_name','/student_users/view')" label="Student's name" prop="student_name">
				<el-input id="student_name" v-model="form['student_name']" placeholder="Please enter student's name"></el-input>
				</el-form-item>
				<el-form-item v-if="$check_register_field('add','student_gender','/student_users/view')" label="Gender of students" prop="student_gender">
				<el-select id="student_gender" v-model="form['student_gender']">
			<el-option v-for="o in list_student_gender" :key="o" :label="o" :value="o">
			</el-option>
		</el-select>
				</el-form-item>
	
	</div>
</template>

<script>
	import mixin from "@/mixins/component.js";

	export default {
		mixins: [mixin],
		props:{

			form:{
				type: Object,
				default: function(){
					return {
							"student_id":  '' ,
								"student_name":  '' ,
								"student_gender":  '' ,
						}
				}
			},

		},
		data() {
			return {
				field: "student_users_id",
				url_add: "~/api/student_users/add?",
				url_set: "~/api/student_users/set?",
				url_upload: "~/api/student_users/upload?",

							list_student_gender: ['male','female'],
	
				rules: {
					"student_id": [    {required: true,message: '学生学号不能为空'},  ],
					"student_name": [    {required: true,message: '学生姓名不能为空'},  ],
					"student_gender": [    {required: true,message: '学生性别不能为空'},  ],
				}

			}
		},
		methods: {

	
	
	

	
	
	

	
	
	

		},
		created() {
					}
	}
</script>
