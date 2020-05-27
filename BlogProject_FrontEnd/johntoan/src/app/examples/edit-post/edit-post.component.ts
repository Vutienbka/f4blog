import { Component, OnInit } from '@angular/core';
import * as DecoupledEditor from '@ckeditor/ckeditor5-build-decoupled-document';
import {Post} from "../model/Post";
import {CategoryEntity} from "../model/CategoryEntity";
import {CommentPost} from "../model/CommentPost";
import {PostLikes} from "../model/PostLikes";
import {TagEntity} from "../model/TagEntity";
import {UserPost} from "../model/UserPost";
import {PostService} from "../../service/post.service";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ChangeEvent} from "@ckeditor/ckeditor5-angular";
@Component({
  selector: 'app-edit-post',
  templateUrl: './edit-post.component.html',
  styleUrls: ['./edit-post.component.css']
})

export class EditPostComponent implements OnInit {
  public Editor = DecoupledEditor;
  postForm:any = FormGroup;
  selectedFiles: File[] = [];
  fileUrl:string;

  private post: Post = new class implements Post {
    categoryEntityList: CategoryEntity[];
    commentsById: CommentPost[];
    content: string;
    createdAt: number;
    id: number;
    postLikesById: PostLikes[];
    publishTime: number;
    publishedStatus: number;
    tagEntityList: TagEntity[];
    title: string;
    updatedAt: number;
    userByUserId: UserPost;
  };
  constructor(private postService: PostService,
              private route:ActivatedRoute,
              private router: Router,
              private httpClient:HttpClient,
              private formBuilder:FormBuilder) {
  }

  private id:number;
  ngOnInit(): void {
    this.route.paramMap.subscribe(param => {
      this.id = +param.get('id');
      this.post = this.postService.getOnePost(this.id);
      if(this.post===undefined){
        this.postService.fetchAllPostFromAPI().subscribe((resJson) => {
          this.postService.postList = resJson;
          this.post = this.postService.getOnePost(this.id);
        });
      }
    });

    this.postForm = this.formBuilder.group({
      title: new FormControl ('',[Validators.required]),
      content: new FormControl('',[Validators.required]),
    });
  }

  onSelectFile(event){
    this.selectedFiles = event.target.files;
    // console.log(this.selectedFiles);
    for (let i=0; i<this.selectedFiles.length ; i++){
      this.selectedFiles.push(event.target.files[i]);
    }
  }

  onSavePost(submitForm: FormGroup) {
    let post = submitForm.value;
    let formData = new FormData();
    formData.append('title', post.title);
    formData.append('content', post.content);
    // console.log(this.selectedFiles);
    if (this.selectedFiles.length > 0) {
      for (let row of this.selectedFiles) {
        formData.append('file[]', row);
      }
    }
    console.log(formData);
    this.postService.updateAfterEdit(formData,this.id).subscribe((response) => {
      console.log(response);
      this.postService.getAllPost();
      this.router.navigate(['showblog/',this.id]);
      let result = confirm("Edited successful. Do you want to leave?");
      if (result == true) {
        this.postService.getOnePost(this.id);
        alert("Thank you for your access!");
        this.router.navigate(['showblog/',this.id]);
      }
      else {
        this.postService.getOnePost(this.id);
        alert("Thank you for staying on page");
        this.router.navigate(['editPost/',this.id]);
      }
    }, (err) => {
      console.log(err, 'error reached');
      this.postService.getOnePost(this.id);
      this.router.navigate(['showblog/',this.id]);
      alert("Not Edited successful");
    });

  }

  viewChangeOfPost( { editor }: ChangeEvent ) {
    const data = editor.getData();
    this.postForm.controls.content.value= data;
    console.log( this.postForm.controls.content.value);
    editor.ui.getEditableElement().parentElement.insertBefore(
        editor.ui.view.toolbar.element,
        editor.ui.getEditableElement()
    );
  }

  reset(){}
  displayFieldCss(field:string){}
  // onChange(media, event){
  //   const mediaFormArray = <FormArray> this.postForm.controls.medias;
  //   if(event.target.checked){
  //     mediaFormArray.push(new FormControl(media));
  //   }else {
  //     let index = mediaFormArray.controls.findIndex(element => element.value == media);
  //     mediaFormArray.removeAt(index);
  //   }
  // }

}
