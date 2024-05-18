<template>
	<div>
			<el-form-item v-if="$check_register_field('add','teachers_name','/teacher_users/view')" label="Teacher's name" prop="teachers_name">
				<el-input id="teachers_name" v-model="form['teachers_name']" placeholder="Please enter teacher's name"></el-input>
				</el-form-item>
				<el-form-item v-if="$check_register_field('add','teacher_id','/teacher_users/view')" label="Teacher's work number" prop="teacher_id">
				<el-input id="teacher_id" v-model="form['teacher_id']" placeholder="Please enter the teacher's job number"></el-input>
				</el-form-item>
				<el-form-item v-if="$check_register_field('add','gender_of_teacher','/teacher_users/view')" label="Gender of teachers" prop="gender_of_teacher">
				<el-select id="gender_of_teacher" v-model="form['gender_of_teacher']">
			<el-option v-for="o in list_gender_of_teacher" :key="o" :label="o" :value="o">
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
							"teachers_name":  '' ,
								"teacher_id":  '' ,
								"gender_of_teacher":  '' ,
						}
				}
			},

		},
		data() {
			return {
				field: "teacher_users_id",
				url_add: "~/api/teacher_users/add?",
				url_set: "~/api/teacher_users/set?",
				url_upload: "~/api/teacher_users/upload?",

							list_gender_of_teacher: ['male','female'],
	
				rules: {
					"teachers_name": [    {required: true,message: '教师姓名不能为空'},  ],
					"teacher_id": [    {required: true,message: '教师工号不能为空'},  ],
					"gender_of_teacher": [     ],
				}

			}
		},
		methods: {

	
	
	

	
	
	

	
	
	

		},
		created() {
					}
	}
</script>
