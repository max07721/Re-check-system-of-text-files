<template>
	<el-main class="bg table_wrap">
		<el-form label-position="right" :model="query" class="form p_4" label-width="120">
			<el-row>
				<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="Teacher's name">
						<el-input v-model="query.teachers_name"></el-input>
					</el-form-item>
				</el-col>
				<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="Student's name">
						<el-input v-model="query.student_name"></el-input>
					</el-form-item>
				</el-col>
				<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="Experimental title">
						<el-input v-model="query.experimental_topic"></el-input>
					</el-form-item>
				</el-col>
				<el-col :xs="24" :sm="10" :lg="8" class="search_btn_wrap_1">
					<el-form-item>
						<el-button type="primary" @click="search()" class="search_btn_find">查询</el-button>
						<el-button @click="reset()" style="margin-right: 74px;" class="search_btn_reset">重置</el-button>
						<router-link
							v-if="user_group == '管理员' || $check_action('/experimental_report/table','add') || $check_action('/experimental_report/view','add')"
							class="el-button el-button--default el-button--primary search_btn_add" to="./view?">添加
						</router-link>
						<el-button
							v-if="user_group == '管理员' || $check_action('/experimental_report/table','del') || $check_action('/experimental_report/view','del')"
							class="search_btn_del" type="danger" @click="delInfo()">删除</el-button>
					</el-form-item>
				</el-col>
			</el-row>
		</el-form>

		<el-table :data="list" @selection-change="selectionChange" @sort-change="$sortChange" style="width: 100%"
			id="dataTable">
			<el-table-column fixed type="selection" tooltip-effect="dark" width="55">
			</el-table-column>
			<el-table-column prop="teacher_account" @sort-change="$sortChange" label="教师账号"
				v-if="user_group == '管理员' || $check_field('get','teacher_account')" min-width="200">
				<template slot-scope="scope">
					{{ get_user_teacher_account(scope.row['teacher_account']) }}
				</template>
			</el-table-column>
			<el-table-column prop="teachers_name" @sort-change="$sortChange" label="教师姓名"
				v-if="user_group == '管理员' || $check_field('get','teachers_name')" min-width="200">
			</el-table-column>
			<el-table-column prop="student_account" @sort-change="$sortChange" label="学生账号"
				v-if="user_group == '管理员' || $check_field('get','student_account')" min-width="200">
				<template slot-scope="scope">
					{{ get_user_student_account(scope.row['student_account']) }}
				</template>
			</el-table-column>
			<el-table-column prop="student_name" @sort-change="$sortChange" label="学生姓名"
				v-if="user_group == '管理员' || $check_field('get','student_name')" min-width="200">
			</el-table-column>
			<el-table-column prop="course_name" @sort-change="$sortChange" label="课程名称"
				v-if="user_group == '管理员' || $check_field('get','course_name')" min-width="200">
			</el-table-column>
			<el-table-column prop="experimental_topic" @sort-change="$sortChange" label="实验题目"
				v-if="user_group == '管理员' || $check_field('get','experimental_topic')" min-width="200">
			</el-table-column>
			<el-table-column prop="experimental_location" @sort-change="$sortChange" label="实验地点"
				v-if="user_group == '管理员' || $check_field('get','experimental_location')" min-width="200">
			</el-table-column>
			<el-table-column prop="experimental_content" @sort-change="$sortChange" label="实验内容"
				v-if="user_group == '管理员' || $check_field('get','experimental_content')" min-width="200">
			</el-table-column>
			<el-table-column prop="experimental_time" @sort-change="$sortChange" label="实验时间"
				v-if="user_group == '管理员' || $check_field('get','experimental_time')" min-width="200">
				<template slot-scope="scope">
					{{ $toTime(scope.row["experimental_time"],"yyyy-MM-dd hh:mm:ss") }}
				</template>
			</el-table-column>
			<el-table-column prop="review_comments" @sort-change="$sortChange" label="批阅评语"
				v-if="user_group == '管理员' || $check_field('get','review_comments')" min-width="200">
			</el-table-column>
			<el-table-column prop="experimental_results" @sort-change="$sortChange" label="实验成绩"
				v-if="user_group == '管理员' || $check_field('get','experimental_results')" min-width="200">
			</el-table-column>




			<el-table-column sortable prop="create_time" label="创建时间" min-width="200">
				<template slot-scope="scope">
					{{ $toTime(scope.row["create_time"],"yyyy-MM-dd hh:mm:ss") }}
				</template>
			</el-table-column>

			<el-table-column sortable prop="update_time" label="更新时间" min-width="200">
				<template slot-scope="scope">
					{{ $toTime(scope.row["update_time"],"yyyy-MM-dd hh:mm:ss") }}
				</template>
			</el-table-column>

			<el-table-column fixed="right" label="操作" min-width="240" align="center"
				v-if="user_group == '管理员' || $check_action('/experimental_report/table','set') || $check_action('/experimental_report/view','set') || $check_action('/experimental_report/view','get') || $check_action('/整体评价/table','add') || $check_action('/整体评价/view','add')">

				<template slot-scope="scope">
					<!--跨表按钮-->
          <router-link class="el-button el-button--small is-plain el-button--success"
                       style="margin: 5px !important;"
                       v-if="user_group == '管理员' || $check_action('/experimental_report/table','set') || $check_action('/experimental_report/view','set') || $check_action('/experimental_report/view','get')"
                       :to="'./view?' + field + '=' + scope.row[field]" size="small">
            <span>详情</span>
          </router-link>
          <el-button class="el-button el-button--small is-plain el-button--default"
                     style="margin: 5px !important;" size="small" @click="btnFn(scope.row)"
                     v-if="user_group == '管理员' || $check_action('/experimental_report/table','set') || $check_action('/experimental_report/view','set') || $check_action('/experimental_report/view','get')">
            <span>查重</span>
          </el-button>
          <el-button class="el-button el-button--small is-plain el-button--default"
                     style="margin: 5px !important;" size="small"
                     @click="to_table(scope.row,'/overall_evaluation/view')"
                     v-if="user_group == '管理员' || $check_action('/overall_evaluation/table','add') || $check_action('/overall_evaluation/view','add')">
            <span>整体评价</span>
          </el-button>

				</template>
			</el-table-column>

		</el-table>

		<!-- 分页器 -->
		<div class="mt text_center">
			<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
				:current-page="query.page" :page-sizes="[7, 10, 30, 100]" :page-size="query.size"
				layout="total, sizes, prev, pager, next, jumper" :total="count">
			</el-pagination>
		</div>
		<!-- /分页器 -->


		<div class="modal_wrap" v-if="showModal">
			<div class="modal_box">
				<!-- <div class="modal_box_close" @click="closeModal">X</div> -->
				<p class="modal_box_title">重要提醒</p>
				<p class="modal_box_text">当前有数据达到预警值！</p>
				<div class="btn_box">
					<span @click="closeModal">取消</span>
					<span @click="closeModal">确定</span>
				</div>
			</div>
		</div>
    <el-dialog title="查重结果" :visible.sync="dialogVisible" width="100%" style="border-radius: 8px" >
      	<span slot="footer" class="dialog-footer">
    		<el-button type="primary" @click="dialogVisible = false">确 定</el-button>
  		</span>
    </el-dialog>

	</el-main>
</template>
<script>
	import mixin from "@/mixins/page.js";
	import {
		saveAs
	} from 'file-saver';

	export default {
		mixins: [mixin],
		data() {
			return {
				text:'',
				dialogVisible:false,
				id1:'',
				id2:'',
				// 弹框
				showModal: false,
				// 获取数据地址
				url_get_list: "~/api/experimental_report/get_list?like=0",
				url_del: "~/api/experimental_report/del?",
				url_export_pdf: "~/api/experimental_report/exportPDF",

				// 字段ID
				field: "experimental_report_id",

				// 查询
				query: {
					"size": 7,
					"page": 1,
					"teachers_name": "",
					"student_name": "",
					"experimental_topic": "",
					"login_time": "",
					"create_time": "",
					"orderby": `create_time desc`
				},
				formDate:{
					targetId:'',
					resourcesId:'',
				},
				rules:{
					targetId: { required: true, message: '请选择', trigger: 'blur' },
					resourcesId: { required: true, message: '请选择', trigger: 'blur' },
				},
				// 数据
				list: [],
				// 用户列表
				list_user_teacher_account: [],
				// 用户列表
				list_user_student_account: [],
			}
		},
		methods: {

      btnFn(data){
		var experimental_report_id_list = this.list
			.filter(item => item.experimental_report_id !== data.experimental_report_id)
			.map(item => item.experimental_report_id);
		console.log(this.list);
		
		let promises = experimental_report_id_list.map(id => {
			return this.$get(`~/api/experimental_report/similarity?targetId=${data.experimental_report_id}&resourcesId=${id}`);
		});

		Promise.all(promises).then(results => {
			let content = results.map(res => res.result).join('&')
			this.$router.push({ name: 'SimilarityResult', query: 
				{ 
					result: content,
					experimental_topic: data.experimental_topic,
					author: data.student_name,
					reportTime: data.create_time,
					experimentalReportId: data.experimental_report_id,
					teacherName: data.teachers_name
				} });
			}).catch(error => {
			// 处理错误情况
			console.error("There was an error fetching the similarity results", error);
		});
      },
	  
      resetFn(){
        this.formDate={
              targetId:'',
              resourcesId:'',
        }
      },
			// 导出pdf
			exportPDF(param) {
				this.$get2("~/api/experimental_report/exportPDF?id=" + param).then(res => {
					console.log(res.headers);
					console.log(res.data)
					console.log("res.data")
					const blob = new Blob([res.data], {
						type: "application/pdf;charset-UTF-8"
					});
					const url = window.URL.createObjectURL(blob);
					saveAs(blob, 'example.pdf');
					window.URL.revokeObjectURL(this.url);

					// 处理返回的文件流
					// console.log(res);
					// let blob = new Blob([res.data],{type:'application/pdf'});
					// //获取fileName,截取content-disposition的filename；按=分割，取最后一个
					// const fileName = res.headers["content-disposition"].split("=")[1];
					// let downloadElement = document.createElement("a");
					// let href = window.URL.createObjectURL(blob); //创建下载的链接
					// downloadElement.href = href;
					// downloadElement.download = fileName; //下载后文件名
					// document.body.appendChild(downloadElement);
					// downloadElement.click(); //点击下载
					// document.body.removeChild(downloadElement); //下载完成移除元素
					// window.URL.revokeObjectURL(href); //释放掉blob对象
					// this.$message.success("pdf已成功导出!");
				});
			},
			// 关闭弹框
			closeModal() {
				this.showModal = false;
			},

			get_list_before(param) {
				var user_group = this.user.user_group;
				if (user_group != "管理员") {
					let sqlwhere = "(";
					if (user_group == "教师用户") {
						sqlwhere += "teacher_account = " + this.user.user_id + " or ";
					}
					if (user_group == "学生用户") {
						sqlwhere += "student_account = " + this.user.user_id + " or ";
					}
					if (sqlwhere.length > 1) {
						sqlwhere = sqlwhere.substr(0, sqlwhere.length - 4);
						sqlwhere += ")";
						param["sqlwhere"] = sqlwhere;
					}
				}
				return param;
			},





			/**
			 * 获取教师用户用户列表
			 */
			async get_list_user_teacher_account() {
				var json = await this.$get("~/api/user/get_list?user_group=教师用户");
				if (json.result && json.result.list) {
					this.list_user_teacher_account = json.result.list;
				} else if (json.error) {
					console.error(json.error);
				}
			},

			get_user_teacher_account(id) {
				var obj = this.list_user_teacher_account.getObj({
					"user_id": id
				});
				var ret = "";
				if (obj) {
					ret = obj.nickname + "-" + obj.username;
					// if(obj.nickname){
					// 	ret = obj.nickname;
					// }
					// else{
					// 	ret = obj.username;
					// }
				}
				return ret;
			},
			/**
			 * 获取学生用户用户列表
			 */
			async get_list_user_student_account() {
				var json = await this.$get("~/api/user/get_list?user_group=学生用户");
				if (json.result && json.result.list) {
					this.list_user_student_account = json.result.list;
				} else if (json.error) {
					console.error(json.error);
				}
			},

			get_user_student_account(id) {
				var obj = this.list_user_student_account.getObj({
					"user_id": id
				});
				var ret = "";
				if (obj) {
					ret = obj.nickname + "-" + obj.username;
					// if(obj.nickname){
					// 	ret = obj.nickname;
					// }
					// else{
					// 	ret = obj.username;
					// }
				}
				return ret;
			},
			deleteRow(index, rows) {
				rows.splice(index, 1);
			}

		},
		created() {
			this.get_list_user_teacher_account();
			this.get_list_user_student_account();
		}
	}
</script>

<style type="text/css" >
.el-dialog{
  border-radius: 8px !important;
}
.formDare{
  position: absolute;
  top: 500px;
}
	.bg {
		background: white;
	}

	.form.p_4 {
		padding: 1rem;
	}

	.form .el-input {
		width: initial;
	}

	.mt {
		margin-top: 1rem;
	}

	.text_center {
		text-align: center;
	}

	.float-right {
		float: right;
	}


	.modal_wrap {
		width: 100vw;
		height: 100vh;
		position: fixed;
		top: 0;
		left: 0;
		background: rgba(0, 0, 0, 0.5);
		z-index: 9999999999;
	}

	.modal_wrap .modal_box {
		width: 400px;
		height: 200px;
		background: url("../../assets/modal_bg.jpg") no-repeat center;
		background-size: cover;
		position: absolute;
		top: 50%;
		left: 50%;
		margin-left: -200px;
		margin-top: -100px;
		border-radius: 10px;
	}

	.modal_wrap .modal_box .modal_box_close {
		font-size: 20px;
		position: absolute;
		top: 10px;
		right: 10px;
		cursor: pointer;
	}

	.modal_wrap .modal_box .modal_box_title {
		text-align: center;
		font-size: 18px;
		margin: 16px auto;
		color: #fff;
		border-bottom: 1px solid rgba(117, 116, 116, 0.5);
		padding-bottom: 16px;
		width: 356px;
	}

	.modal_wrap .modal_box .modal_box_text {
		text-align: center;
		font-size: 15px;
		color: #fff;
		margin-top: 25px;
	}

	.modal_wrap .modal_box .btn_box {
		display: flex;
		flex-direction: row;
		justify-content: center;
		margin-top: 42px;
	}

	.modal_wrap .modal_box .btn_box span {
		display: inline-block;
		width: 80px;
		height: 30px;
		line-height: 30px;
		text-align: center;
		border: 1px solid #ccc;
		font-size: 14px;
		cursor: pointer;
		color: #fff;
	}

	.modal_wrap .modal_box .btn_box span:nth-child(2) {
		background: #409EFF;
		color: #fff;
		border-color: #409EFF;
		margin-left: 15px;
	}
</style>
