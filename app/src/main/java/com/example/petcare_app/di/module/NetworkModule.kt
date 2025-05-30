package com.example.petcare_app.di.module

import TokenDataStore
import com.example.petcare_app.data.network.RetrofitInstance
import com.example.petcare_app.data.repository.CpfValidationRepository
import com.example.petcare_app.data.repository.ImageRepository
import com.example.petcare_app.data.repository.LoginRepository
import com.example.petcare_app.data.repository.PetRepository
import com.example.petcare_app.data.repository.PlanRepository
import com.example.petcare_app.data.repository.RaceRepository
import com.example.petcare_app.data.repository.ScheduleRepository
import com.example.petcare_app.data.repository.SizeRepository
import com.example.petcare_app.data.repository.SpecieRepository
import com.example.petcare_app.data.repository.UserRepository
import com.example.petcare_app.data.services.CpfValidationService
import com.example.petcare_app.data.services.ImageService
import com.example.petcare_app.data.services.LoginService
import com.example.petcare_app.data.services.PetService
import com.example.petcare_app.data.services.PlanService
import com.example.petcare_app.data.services.RaceService
import com.example.petcare_app.data.services.ScheduleService
import com.example.petcare_app.data.services.SizeService
import com.example.petcare_app.data.services.SpecieService
import com.example.petcare_app.data.services.UserService
import com.example.petcare_app.data.viewmodel.CpfValidationViewModel
import com.example.petcare_app.data.viewmodel.EditUserViewModel
import com.example.petcare_app.data.viewmodel.LoginViewModel
import com.example.petcare_app.data.viewmodel.SchedulesHomeAppViewModel
import com.example.petcare_app.data.viewmodel.SignUpViewModel
import com.example.petcare_app.data.viewmodel.VerifyTokenViewModel
import org.koin.core.module.dsl.viewModel

import org.koin.dsl.module


val appModule = module{

    single<TokenDataStore> {
        TokenDataStore(get())
    }

    single <CpfValidationService> {
        RetrofitInstance.retrofit.create(CpfValidationService::class.java)
    }

    single <CpfValidationRepository> {
        CpfValidationRepository(get())
    }

    single <ImageService> {
        RetrofitInstance.retrofit.create(ImageService::class.java)
    }

    single <ImageRepository> {
        ImageRepository(get())
    }

    single <LoginService> {
        RetrofitInstance.retrofit.create(LoginService::class.java)
    }

    single <LoginRepository> {
        LoginRepository(get())
    }

    single <PetService> {
        RetrofitInstance.retrofit.create(PetService::class.java)
    }

    single <PetRepository> {
        PetRepository(get())
    }

    single <PlanService>{
        RetrofitInstance.retrofit.create(PlanService::class.java)
    }

    single <PlanRepository> {
        PlanRepository(get())
    }

    single <RaceService> {
        RetrofitInstance.retrofit.create(RaceService::class.java)
    }

    single <RaceRepository> {
        RaceRepository(get())
    }

    single <ScheduleService> {
        RetrofitInstance.retrofit.create(ScheduleService::class.java)
    }

    single <ScheduleRepository> {
        ScheduleRepository(get())
    }

    single <SizeService> {
        RetrofitInstance.retrofit.create(SizeService::class.java)
    }

    single <SizeRepository> {
        SizeRepository(get())
    }

    single <SpecieService> {
        RetrofitInstance.retrofit.create(SpecieService::class.java)
    }

    single <SpecieRepository> {
        SpecieRepository(get())
    }

    single <UserService> {
        RetrofitInstance.retrofit.create(UserService::class.java)
    }

    single <UserRepository> {
        UserRepository(get())
    }

    viewModel<EditUserViewModel> {
        EditUserViewModel(get(), get(), get())
    }

    viewModel<CpfValidationViewModel> {
        CpfValidationViewModel(get())
    }

    viewModel<LoginViewModel> {
        LoginViewModel(get(), get())
    }

    viewModel<SchedulesHomeAppViewModel> {
        SchedulesHomeAppViewModel(get(), get())
    }

    viewModel<SignUpViewModel> {
        SignUpViewModel(get(), get(), get(), get(), get())
    }

    viewModel <VerifyTokenViewModel> {
        VerifyTokenViewModel(get())
    }

}