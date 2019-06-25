5.times do
  Food.create({
    dish: Faker::Food.dish,
    measurement: Faker::Food.measurement
    })
end
